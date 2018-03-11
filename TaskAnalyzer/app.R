
# Mpower Analyzer

library(shiny)
library(reshape2)
library(RColorBrewer)
library(ggplot2)

# data = read.csv("https://raw.githubusercontent.com/noelleisanerd/Mpower/master/sample_list_filled.csv")
data = read.csv(file.choose(),stringsAsFactors = F)
data = data[,c("time_start","time_end","category","value","col")]
data$category = gsub(" ","",data$category)

data$time = data$time_start
data$time = as.POSIXct(data$time)

colors = data.frame(category = unique(data$category),
                    color = brewer.pal(n=length(unique(data$category)),name="Set3"))

melted.df = merge(data,colors,by="category")

melted.df$value[melted.df$category == "take_med" & melted.df$value==1] = NA
melted.df$value[melted.df$category == "take_med" & melted.df$value==0] = 1

merge.df = melted.df[which(melted.df$category != "take_med" &
                             melted.df$category != "report_pain" &
                             melted.df$category != "comment") , ]
merge.df$value = as.numeric(as.character(merge.df$value))



# Define UI for application that draws a histogram
ui <- fluidPage(
  
  tags$style(type="text/css",
             ".shiny-output-error { visibility: hidden; }",
             ".shiny-output-error:before { visibility: hidden; }"),
  
  fluidRow(
    navbarPage(" Mpower ----> ",
               
               tabPanel("Overview",
                        sidebarPanel(
                          
                          sliderInput("zoom",
                                      "ZOOM in/out", 
                                      min=min(melted.df$time),
                                      max=max(melted.df$time),
                                      value=c(min(melted.df$time),
                                              max(melted.df$time) ),
                                      timeFormat="%F %T"),
                          
                          selectInput("selection", "Select Category", multiple = T,
                                      choices = unique(merge.df$category),
                                      selected = ""),
                          
                          checkboxInput("show.primary", "Show primary outcome",
                                        value=F),
                          checkboxInput("show.adherence", "Show med. adherence",
                                        value=F),
                          checkboxInput("show.trends", "Show trends",
                                        value=F),
                          checkboxInput("show.comments", "Show comments",
                                        value=F),
                          
                          width=3),
                        
                        mainPanel(plotOutput("pft.plot"))),
               
               
               tabPanel("place-holder: Upload your data",
                        sidebarPanel(
                          fileInput("file1", "Choose CSV File",
                                    multiple = TRUE,
                                    accept = c("text/csv",
                                               "text/comma-separated-values,text/plain",
                                               ".csv")),
                          
                          width=3))
    )))




# Define server logic required to draw a histogram
server <- function(input, output) {
  
  
  
  
  output$pft.plot <- renderPlot({
    height ="auto"
    
    df.selection = merge.df[merge.df$category %in% input$selection,]
    df.selection = df.selection[order(df.selection$time),]
    if(input$show.trends==T & length(df.selection[,1]>0)){
    trend.df = df.selection[1,]
    trend.df$category = as.character(trend.df$category)
    for(i in 1:length(unique(df.selection$category ))){
      if(length(df.selection$value[df.selection$category==unique(df.selection$category)[i]])>2){
      temp = as.numeric(stl(ts(df.selection$value[df.selection$category==unique(df.selection$category)[i]],
                               frequency=21),
                            "periodic")$time.series[,2])
      temp.df = df.selection[df.selection$category==unique(df.selection$category)[i],]
      temp.df$value = temp
      trend.df = rbind(trend.df,temp.df)
      }
    }
    trend.df$category = as.factor(trend.df$category) 
    main.plot =
      ggplot() +
      geom_point(data=df.selection,aes(x=time,y=as.numeric(value),color=category),size=2,alpha=0.5) +
      geom_line(data=df.selection,aes(x=time,y=as.numeric(value),color=category), alpha=0.3) +
      geom_line(data=trend.df,aes(x=time,y=as.numeric(value),color=category),size=1) +
      # geom_line(size=1.5) +
      xlim((input$zoom[1]),(input$zoom[2])) +
      ylab("Level") +
      xlab("Date") +
      # scale_colour_manual("Legend", values = unique(df.selection$color)) +
      theme(legend.position = "top",
            legend.text=element_text(size=18),
            legend.title=element_text(size=20))
    } else {
      main.plot =
        ggplot() +
        geom_point(data=df.selection,aes(x=time,y=as.numeric(value),color=category)) +
        geom_line(data=df.selection,aes(x=time,y=as.numeric(value),color=category), alpha=0.5) +
        # geom_line(data=trend.df,aes(x=time,y=value,color=category),size=1) +
        # geom_line(size=1.5) +
        xlim((input$zoom[1]),(input$zoom[2])) +
        ylab("Level") +
        xlab("Date") +
        # scale_colour_manual("Legend", values = unique(df.selection$color)) +
        theme(legend.position = "top",
              legend.text=element_text(size=18),
              legend.title=element_text(size=20))
    }
    
    
    if(input$show.primary==T){
      main.plot=
        main.plot +
        geom_line(data=melted.df[melted.df$category=="report_pain",],aes(x=time,y=as.numeric(value),color=category),size=2,alpha=0.9,col="purple")
    }
    
    if(input$show.adherence==T){
      main.plot=
        main.plot +
        geom_jitter(data=melted.df[melted.df$category=="take_med",],
                    aes(x=time,y=as.numeric(value),color=category),shape="|",size=10,alpha=0.9,col="darkred")
    }
    
    if(input$show.comments==T){
      main.plot=
        main.plot +
        annotate("text", x=melted.df$time[melted.df$category=="comment"], 
                 y=50, 
                 label= melted.df$value[melted.df$category=="comment"]) +
        geom_point(aes(x=melted.df$time[melted.df$category=="comment"], 
                   y=55),shape="!",col="red",size=7)
    }
    
    
    
    
    main.plot
    
  }, height = 480, width = "auto")
}

# Run the application 
shinyApp(ui = ui, server = server)

