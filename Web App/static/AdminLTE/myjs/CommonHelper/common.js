function PrintList(list){
    list.forEach(item =>{
        console.log(item)
    })
}

function PrintListPrivateData(list){
    console.log('-----------------------------------')
    list.forEach(item =>{
        console.log(item._private.data)
    })
}

// Input x% => Ouput: Color code
function perc2color(perc) {
	let  r, g, b = 0;
	if(perc < 50) {
		r = 200;
		g = Math.round(5.1 * perc);
	}
	else {
		g = 200;
		r = Math.round(510 - 5.10 * perc);
	}
	let  h = r * 0x10000 + g * 0x100 + b * 0x1;

	let colorCode = '#' + ('000000' + h.toString(16)).slice(-6);
	console.log(perc, colorCode)
	return colorCode
}