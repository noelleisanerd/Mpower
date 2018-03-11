# savegame

#
# This is a Shiny web application. You can run the application by clicking
# the 'Run App' button above.
#
# Find out more about building applications with Shiny here:
#
#    http://shiny.rstudio.com/
#

library(shiny)
library(reshape2)
library(RColorBrewer)
library(ggplot2)

# data = read.csv("https://raw.githubusercontent.com/noelleisanerd/Mpower/master/sample_list_filled.csv")
data = read.csv(file.choose())
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
                             melted.df$category != "report_pain") ,]



# Define UI for application that draws a histogram
ui <- fluidPage(
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
                                        value=T),
                          checkboxInput("show.adherence", "Show med. adherence",
                                        value=T),
                          
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
    # df.selection = df.selection[-which(df.selection$category=="report_pain"),]
    # df.selection = df.selection[-which(df.selection$category=="take_med"),]
    # 
    main.plot =
      ggplot(df.selection,aes(x=time,y=value,color=category)) +
      geom_point() +
      geom_line(alpha=0.3) +
      geom_line(size=1.5) +
      xlim((input$zoom[1]),(input$zoom[2])) +
      ylab("Change (SD)") +
      xlab("Date") +
      scale_colour_manual("Legend", values = unique(df.selection$color)) +
      theme(legend.position = "top",
            legend.text=element_text(size=18),
            legend.title=element_text(size=20))
    
    
    if(input$show.primary==T){
      main.plot=
        main.plot +
        geom_line(data=melted.df[melted.df$category=="report_pain",],aes(x=time,y=value,color=category),size=2,alpha=0.9,col="black")
    }
    
    if(input$show.adherence==T){
      main.plot=
        main.plot +
        geom_jitter(data=melted.df[melted.df$category=="take_med",],
                    aes(x=time,y=value,color=category),shape="|",size=10,alpha=0.9,col="darkred")
    }
    
    
    
    
    
    main.plot
    
  }, height = 480, width = "auto")
}

# Run the application 
shinyApp(ui = ui, server = server)

