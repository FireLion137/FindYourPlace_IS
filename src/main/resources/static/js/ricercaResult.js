
function drawChart(x,y){
    let labels=[];
    let numbers=[];
    for (let i=0;i<list.length;i++){
        labels.push(list[i].nome)
        if (y==='numAbitanti')
            numbers.push(list[i].numAbitanti);
        if (y==='qualityIndex')
            numbers.push(list[i].qualityIndex);
        if (y==='numNegozi')
            numbers.push(list[i].numNegozi);
        if (y==='numScuole')
            numbers.push(list[i].numScuole);
        if (y==='numRistoranti')
            numbers.push(list[i].numRistoranti);
        if (y==='danger')
            numbers.push(list[i].danger);
    }
    let title;
    if (y==='numAbitanti')
        title='Numero Abitanti'
    if (y==='qualityIndex')
        title='indice di qualità'
    if (y==='numNegozi')
        title='Numero Negozi'
    if (y==='numScuole')
        title='Numero Scuole'
    if (y==='numRistoranti')
        title='Numero Ristoranti'
    if (y==='danger')
        title='Pericolosità'

    const data = {
        labels: labels,
        datasets: [{
            label: title,
            data: numbers,
            backgroundColor: "rgba(255,99,132,0.2)",
            borderColor: "rgba(255,99,132,1)",
            borderWidth: 2,
            hoverBackgroundColor: "rgba(255,99,132,0.4)",
            hoverBorderColor: "rgba(255,99,132,1)",
            indexAxis: 'y',
        }]
    };

    const options = {
        type: 'bar',
        data: data,
        options: {
            indexAxis: 'y',
            responsive: true,
            maintainAspectRatio: false
        },
    };

    let element=document.getElementById('SearchResult-charts-'+x);

    new Chart(element, {
        type: 'bar',
        options: options,
        data: data
    });
}

$(document).ready(function (){
    drawChart('numAbb','numAbitanti');
    drawChart('idq', 'qualityIndex');
    drawChart('numNeg','numNegozi');
    drawChart('numScu','numScuole');
    drawChart('numRist','numRistoranti');
    drawChart('pericolosita','danger');
})












