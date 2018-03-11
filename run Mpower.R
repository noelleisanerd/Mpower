# 
#  M_power - 


# Set up a treatment plan
create_path = "https://raw.githubusercontent.com/noelleisanerd/Mpower/master/TaskCreator/app.R"
M_power_create <- RCurl::getURL(create_path)
eval(parse(text = M_power_create)) 


# Analyse the treatment plan
analyze_path = "https://raw.githubusercontent.com/noelleisanerd/Mpower/master/TaskAnalyzer/app.R"
M_power_analyze <- RCurl::getURL(analyze_path)
eval(parse(text = M_power_analyze)) 
