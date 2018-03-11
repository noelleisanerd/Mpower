
# __M_power__: Person-centered solution for data-driven healthcare

Look at us! We made a thing!
Someone shoudl explain what this is about...

<br><br>
****
<br><br>

## __M_power Flow__

  * Define problem(s):  Management of chronic pain
  * Primary objective: Reduced levels of pain
  * Secondary objectives: Improve well-being, improve sleep, more activity
  * Measurement instruments: e.g. Numeric scales, hours of sleep, etc.
  * Plan interventions: 1. Opioids (2 weeks), 2. Amitriptyline (2 weeks), Physiotherapy (2 weeks)
  * Do
  * Evaluate
<br><br>
****
<br><br>
## Pieces

### Set up a treatment plan
```
# Run R-code to set up a treatment plan
create_path = "https://raw.githubusercontent.com/noelleisanerd/Mpower/master/TaskCreator/app.R"
M_power_create <- RCurl::getURL(create_path)
eval(parse(text = M_power_create)) 
```

### Run mobile app
```
# ...
```

### Analyze the results 
```
# Run R-code to analyze treatment results
analyze_path = "https://raw.githubusercontent.com/noelleisanerd/Mpower/master/TaskAnalyzer/app.R"
M_power_analyze <- RCurl::getURL(analyze_path)
eval(parse(text = M_power_analyze)) 
```

; )

