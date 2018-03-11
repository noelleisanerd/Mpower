
# Mpower Task Creator

library(shiny)

ui <- fluidPage(
  pageWithSidebar(headerPanel("Adding entries to table"),
                  sidebarPanel(textInput("text1", "Start in week"),
                               textInput("text2", "End after week"),
                               selectInput("whichday", "on which days?", 
                                           choices = weekdays.Date(as.Date((Sys.Date():(Sys.Date()+6)),origin=as.Date("1970-01-01"))),
                                           selected = weekdays.Date(as.Date((Sys.Date():(Sys.Date()+6)),origin=as.Date("1970-01-01"))),
                                           multiple = T),
                               selectInput("Set.time", "When on the day?", 
                                           choices = c("morning","noon","afternoon","evening","night"),
                                           selected = c("morning"),
                                           multiple = T),
                               
                               
                               textInput("text4", "Category"),
                               selectInput("class.input", "Class?", 
                                           choices = c("Numbers 1-10",
                                                       "Numbers 1-5",
                                                       "yes, no",
                                                       "free text",
                                                       "Other"),
                                           multiple = F),
                               
                               
                               actionButton("do", "Update Table"),
                               downloadButton("downloadData", "Download"),
                               br(),
                               br(),
                               br(),
                               actionButton("undo", "Clear all")),
                  mainPanel(tableOutput("table1"))))



server <- function(input, output,session) {
  values <- reactiveValues()
  values$df <- data.frame(time = NA,
                          category = NA,
                          value = NA,
                          class = NA)
  values$matrix = matrix()
  newEntry <- observe({
    observeEvent(input$do, {
      
      
      
      today = Sys.Date() 
      seq =  isolate(seq(from= as.numeric(input$text1)*7-6, 
                         to = as.numeric(input$text2)*7))
      seq = today + seq
      wd.index = weekdays(seq)
      seq = seq[wd.index %in% input$whichday]
      cat = isolate(input$text4)
      when = isolate(input$Set.time)
      seq = rep(seq,each=length(when))
      when = ifelse(when == "morning","09:00",
                    ifelse(when == "noon","12:00",
                           ifelse(when == "afternoon","15:00",
                                  ifelse(when == "evening","18:00",
                                         ifelse(when == "night","21:00","12:00")))))
      
      time = isolate(paste(seq,when))
      class.selection = isolate(input$class.input)
      mat = data.frame(time = time,
                       category = cat,
                       value = "",
                       class=class.selection)
      isolate(values$matrix <- mat )
      isolate(values$df <- rbind(values$df,values$matrix))
      
      
      
    })
    observeEvent(input$undo, {
      isolate(values$df <- data.frame(time = NA,
                                      category = NA,
                                      value = NA,
                                      class=NA))
    })
  }
  
  )
  
  
  
  output$table1 <- renderTable({values$df})
  
  output$downloadData <- downloadHandler(
    
    filename = "MpowerTasks.csv",
    content = function(file) {
      write.csv(values$df, "MpowerTasks.csv", row.names = FALSE)
      
      system("git add /users/waqr/desktop/Mpower/TaskCreator/MpowerTasks.csv")
      system('git commit -m "commit MpowerTask.csv"')
      system("git push")
      
    }
  )
  
  
  
  
  
}

shinyApp(ui = ui, server = server)
