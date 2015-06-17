[QueryGroup="Geographical"] @collection [[
[QueryItem="CapitalOfProvinces"]
#Find the capitals of all provinces
PREFIX : <http://www.example.org/monidal.owl#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
select distinct ?x ?y where {
  ?y rdf:type :Province. ?y :provinceHasCapital ?x. ?x rdf:type :City}

[QueryItem="CitiesInSomeProvince"]
#Select all the cities in province Andalusia
PREFIX : <http://www.example.org/monidal.owl#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
select distinct ?city ?province where {
  ?x rdf:type :City. ?x :cityName ?city. ?y rdf:type :Province. ?y :provinceName ?province. ?x :cityInProvince ?y. 
  ?y :provinceName "Andalusia"^^xsd:string}

[QueryItem="CitiesWithArbitraryName"]
#Select city in province where province name includes the word "and"
PREFIX : <http://www.example.org/monidal.owl#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
select distinct ?z where {
  ?x rdf:type :City. ?y rdf:type :Province. ?x :cityInProvince ?y.  
   ?y :provinceName ?z.  
  FILTER regex(?z, "^and", "i") }

[QueryItem="CitiesWithArbitraryName2"]
#Select all the cities which have in their names word "and"
PREFIX : <http://www.example.org/monidal.owl#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
select distinct ?x ?k where {
  ?x rdf:type :City. ?x :cityName ?k.
  FILTER regex(?k, "^and", "i") }

[QueryItem="CapitalCities"]
#Select all the capitals of countries
PREFIX : <http://www.example.org/monidal.owl#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
select distinct ?c ?n where {
  ?x rdf:type :Country. ?x :countryName ?c. ?x  :countryHasCapital ?y. ?y :cityName ?n}

[QueryItem="LakesInProvinces"]
#Select all the lakes in provinces
PREFIX : <http://www.example.org/monidal.owl#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
select distinct ?province ?lake where {
?y rdf:type :Province. ?y :provinceName ?province. ?x rdf:type :Lake. ?x :waterName ?lake.  ?x :lakeInProvince ?y}

[QueryItem="RiverInProvince"]
#Select rivers in provinces
PREFIX : <http://www.example.org/monidal.owl#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
select distinct ?river ?province where {
?x rdf:type :River. ?x :waterName ?river.  ?y rdf:type :Province. ?y :provinceName ?province.   ?x :riverInProvince ?y}

[QueryItem="SeasGeneral"]
#Select seas in provinces
PREFIX : <http://www.example.org/monidal.owl#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
select distinct ?name ?province where {
 ?x :waterName ?name. ?y :provinceName ?province.   ?x :seaInProvince ?y}

[QueryItem="WaterWithDifferentiatedTypes"]
#Select water elements in provinces
PREFIX : <http://www.example.org/monidal.owl#>
select distinct ?name ?province ?type where {
 ?x :waterName ?name. ?x :waterInProvince ?province.   ?x :waterType ?type}

[QueryItem="Surface-Types"]
#Select different elements of geomorphology in provinces
PREFIX : <http://www.example.org/monidal.owl#>
select  ?name ?province ?type where {
 ?s :surfaceName ?name. ?s :surfaceInProvince ?province.   ?s :surfaceType ?type}

[QueryItem="MountainsInProvinces"]
#Select all mountains in provinces
PREFIX : <http://www.example.org/monidal.owl#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
select distinct ?province ?mountain where {
?y rdf:type :Province. ?y :provinceName ?province. ?x rdf:type :Mountain. ?x :surfaceName ?mountain.  ?x :mountainInProvince ?y}

[QueryItem="DesertsInProvinces"]
#Select all deserts in provinces
PREFIX : <http://www.example.org/monidal.owl#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
select distinct ?province ?desert where {
?y rdf:type :Province. ?y :provinceName ?province. ?x rdf:type :Desert. ?x :surfaceName ?desert.  ?x :desertInProvince ?y}

[QueryItem="IslandsInProvince"]
#Select all different islands in provinces
PREFIX : <http://www.example.org/monidal.owl#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
select distinct ?province ?island where {
?y rdf:type :Province. ?y :provinceName ?province. ?x rdf:type :Island. ?x :surfaceName ?island.  ?x :islandInProvince ?y}

[QueryItem="BordersOfSpecificCountry"]
#Neighbouring countries for specified country - in this example Angola
PREFIX : <http://www.example.org/monidal.owl#> 
select *  where {
{
select distinct ?c2 ?country ?capital ?population ?area ?continent ?border where {  ?c2 :countryName ?country. ?en :encompass1 ?c2. ?en :encompass2 ?con.    ?con :continentName ?continent.   ?c2 :countryHasCapital ?ci. ?ci :cityName ?capital.   ?b :border1 ?c1.  ?b :border2 ?c2.  ?b :borderLength ?border.  FILTER (?c1 = <http://www.example.org/monidal.owl#country/ANG>).   ?c2 :countryArea ?area. ?c2 :countryPopulation ?population.} 

 } UNION { 
select distinct ?c2 ?country ?capital ?population ?area ?continent ?border where {  ?c2 :countryName ?country. ?en :encompass1 ?c2. ?en :encompass2 ?con.    ?con :continentName ?continent.   ?c2 :countryHasCapital ?ci. ?ci :cityName ?capital.   ?b :border1 ?c2.  ?b :border2 ?c1.  ?b :borderLength ?border.  FILTER (?c1 = <http://www.example.org/monidal.owl#country/ANG>).   ?c2 :countryArea ?area. ?c2 :countryPopulation ?population.} 
} 
}
]]

[QueryGroup="Language"] @collection [[
[QueryItem="PercentageOfLanguages"]
#Percentage of languages in countries
PREFIX : <http://www.example.org/monidal.owl#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
select ?language ?country ?percentage  {
?x rdf:type :Language. ?x :languagePercentage ?percentage. ?x :languageName ?language. ?y rdf:type :Country. ?y :countryName ?country. ?x :languageCountry ?y}
]]

[QueryGroup="Politics"] @collection [[
[QueryItem="Simple"]
# The list of communist countries
PREFIX : <http://www.example.org/monidal.owl#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
select distinct ?country  where {
  ?x :countryName ?country. ?y :politicsGovernment ?gov. ?y :governmentInCountry ?x.
  FILTER regex(str(?gov), "Communist state"). }
]]

[QueryGroup="Economics"] @collection [[
[QueryItem="InflationCountry"]
#Find the countries with the lowest inflation rate in europe
PREFIX : <http://www.example.org/monidal.owl#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
select ?country ?inflation ?continent where {
  ?economy :economyInCountry ?co. ?economy :economyInflation ?inflation. ?co :countryName ?country. ?enc rdf:type :Encompass.
?enc :encompass1 ?co. ?enc :encompass2 ?cont. ?cont :continentName ?continent.
filter regex(str(?continent) ,"Europe")}

[QueryItem="CountryGDP"]
#Countries with highest GDP
]]

[QueryGroup="Organizations"] @collection [[
[QueryItem="CountriesMemberOfOrganization"]
#List the name of countries which are a member of NATO. We need reification
PREFIX : <http://www.example.org/monidal.owl#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
select ?organization ?country
where {?mem :memberCountry ?co. ?mem :memberOrganization ?org. 
?org :organizationName ?organization. ?co :countryName ?country. ?org :organizationAbbreviation ?orgabv
filter regex(str(?orgabv) ,"NATO") }
]]

[QueryGroup="Religion"] @collection [[
[QueryItem="CountryReligion"]
PREFIX : <http://www.example.org/monidal.owl#> 
select distinct  ?co ?country ?name ?percentage where {  ?co :countryName ?country. ?rel :religionInCountry ?co.   ?rel :religionName ?name. OPTIONAL { ?rel :religionPercentage ?percentage.}  }

[QueryItem="ReligionsInCountries"]
//Religion in specific country
PREFIX : <http://www.example.org/monidal.owl#> 
select distinct ?country ?gdp ?industry ?services  ?inflation ?continent ?co where {  ?co :countryName ?country. ?en :encompass1 ?co. ?en :encompass2 ?con.    ?con :continentName ?continent.   ?ec :economyInCountry ?co.   OPTIONAL {?ec :economyGdp ?gdp. } OPTIONAL { ?ec :economyIndustry ?industry.}   OPTIONAL {?ec :economyService ?services.} OPTIONAL {?ec :economyInflation ?inflation.} FILTER (?co = <http://www.example.org/monidal.owl#country/LB>).  }
]]
