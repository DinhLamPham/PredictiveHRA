


function ShowGraphViz(){

	let check_graphviz = document.getElementById('graph');
	if(check_graphviz !== null) {
		alert('Scroll down for showing result!')
		// Preprocessing here...
		GenerateGraphviz()
	}
}


function GenerateGraphviz(){
	var graphviz = d3.select("#graph").graphviz();

	function render() {
    var transition1 = d3.transition()
        .ease(d3.easeLinear)
        .duration(0);

    graphviz
        .dot(dot)
        .transition(transition1)
        .render();

    console.log(dot);

}

var dot = `
digraph {
    subgraph clusterstage_0 {
	label="Stage 0"
	node [shape=circle, style="wedged", fixedsize=true, width="1.4px"]
	a0 [label="hadoopFile", fillcolor="yellow;0.8:orange"]
	a1 [label="map", fillcolor="yellow;0.8:orange"]
	a2 [label="filterWithProfiling", fontsize=11, fillcolor="yellow;0.3:orange", tooltip = "success:0.3 \nfail:0.7"]
	a3 [label="flatMapWithProfiling", fontsize=11]
	a4 [label="watchpoint"]
	a5 [label="map"]
	a0->a1->a2->a3->a4->a5
    }
    subgraph clusterstage_1 {
	label="Stage 1"
	node [shape=circle, style="wedged", fixedsize=true, width="1.4px"]
	b0 [label = "reduceByKey", fontsize=12.5, fillcolor="yellow;0.5:orange"]
	b1 [label = "map", fillcolor="yellow;0.5:orange"]
	b0->b1
    }
}
`;

render();

}
