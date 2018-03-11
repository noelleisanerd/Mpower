
# __M_power__: Person-centered solution for data-driven healthcare

Look at us! We made a thing!
Someone shoudl explain what this is about...

<br><br>
****
<br><br>


## __The future of health care__

* Person-centered: Focusing on patient’s preferences

* Prolonged: Best level of ‘management’ for chronic care 

* Personalized: Requires new types of trials and evidence

* Patient-led: Empowering patients to own their health


<br><br>
****
<br><br>



## __M_power Flow__

  1. Define problem(s):  Management of chronic pain
  2. Primary objective: Reduced levels of pain
  3. Secondary objectives: Improve well-being, improve sleep, more activity
  4. Measurement instruments: e.g. Numeric scales, hours of sleep, etc.
  5. Plan interventions: 1. Opioids (2 weeks), 2. Amitriptyline (2 weeks), Physiotherapy (2 weeks)
  6. Do
  7. Evaluate
  
<br><br>
****
<br><br>

## Pieces of R-code

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

<br><br><br><br>


![alt text](https://github.com/noelleisanerd/Mpower/raw/master/dcfc76fc31d70ed84c379fa2737204f4.jpg "23")
