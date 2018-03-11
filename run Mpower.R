


# Set up a treatment plan
Mpower_create <- RCurl::getURL("https://raw.githubusercontent.com/noelleisanerd/Mpower/master/TaskCreator/app.R")
eval(parse(text = Mpower_create)) 

# Analyse the treatment plan
Mpower_analyze <- RCurl::getURL("https://raw.githubusercontent.com/noelleisanerd/Mpower/master/TaskAnalyzer/app.R")
eval(parse(text = Mpower_analyze)) 
