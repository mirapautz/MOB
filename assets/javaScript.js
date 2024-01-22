google.charts.load('current', {
    'packages':['geochart'],
    // Note: you will need to get a mapsApiKey for your project.
    // See: https://developers.google.com/chart/interactive/docs/basic_load_libs#load-settings
    'mapsApiKey': 'AIzaSyD-9tSrke72PouQMnMX-a7eZSW0jkFMBWY',
});
google.charts.setOnLoadCallback(drawRegionsMap);

var i = 100
var chart

function drawRegionsMap() {

}

function tableData(strData){

   var dataArray = [
        ['Country', 'Infections'],
        ['Germany', 0],

    ]
    //Splitet übergebenen String in Land/Wert Paare auf.
    const countryValuePair = strData.split('#');
    console.log(countryValuePair)
    countryValuePair.forEach(function (currentValue){

        const splitArray = currentValue.split('+')
        console.log(splitArray)
        dataArray.push(splitArray)
    })

    console.log(dataArray.toString())
    var data = google.visualization.arrayToDataTable(dataArray);

    var options = {
        colorAxis: {colors: ['#ff0000']}
    };

    chart = new google.visualization.GeoChart(document.getElementById('regions_div'));
    chart.draw(data, options);
}