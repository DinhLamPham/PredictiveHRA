function Cose_Layout(option){
    let cose_layout = {
        name: 'cose',
        animate: true,
        idealEdgeLength: 100,
        nodeOverlap: 20,
        refresh: 20,
        fit: true,
        padding: 30,
        randomize: true,
        componentSpacing: 100,
        nodeRepulsion: 400000,
        edgeElasticity: 100,
        nestingFactor: 5,
        gravity: 80,
        numIter: 1000,
        initialTemp: 200,
        coolingFactor: 0.95,
        minTemp: 1.0,
        maxSimulationTime: 15000,

    };
    let random_layout = {
          name: 'random',

          fit: true, // whether to fit to viewport
          padding: 30, // fit padding
          boundingBox: undefined, // constrain layout bounds; { x1, y1, x2, y2 } or { x1, y1, w, h }
          animate: false, // whether to transition the node positions
          animationDuration: 500, // duration of animation in ms if enabled
          animationEasing: undefined, // easing of animation if enabled
          animateFilter: function ( node, i ){ return true; }, // a function that determines whether the node should be animated.  All nodes animated by default on animate enabled.  Non-animated nodes are positioned immediately when the layout starts
          ready: undefined, // callback on layoutready
          stop: undefined, // callback on layoutstop
          transform: function (node, position ){ return position; } // transform a given node position. Useful for changing flow direction in discrete layouts
    };

    let preset_layout = {
          name: 'preset',

          positions: undefined, // map of (node id) => (position obj); or function(node){ return somPos; }
          zoom: undefined, // the zoom level to set (prob want fit = false if set)
          pan: undefined, // the pan level to set (prob want fit = false if set)
          fit: true, // whether to fit to viewport
          padding: 30, // padding on fit
          animate: true, // whether to transition the node positions
          animationDuration: 500, // duration of animation in ms if enabled
          animationEasing: undefined, // easing of animation if enabled
          animateFilter: function ( node, i ){ return true; }, // a function that determines whether the node should be animated.  All nodes animated by default on animate enabled.  Non-animated nodes are positioned immediately when the layout starts
          ready: undefined, // callback on layoutready
          stop: undefined, // callback on layoutstop
          transform: function (node, position ){ return position; } // transform a given node position. Useful for changing flow direction in discrete layouts
        };

    let grid_layout = {
          name: 'grid',

          fit: true, // whether to fit the viewport to the graph
          padding: 30, // padding used on fit
          boundingBox: undefined, // constrain layout bounds; { x1, y1, x2, y2 } or { x1, y1, w, h }
          avoidOverlap: true, // prevents node overlap, may overflow boundingBox if not enough space
          avoidOverlapPadding: 10, // extra spacing around nodes when avoidOverlap: true
          nodeDimensionsIncludeLabels: false, // Excludes the label when calculating node bounding boxes for the layout algorithm
          spacingFactor: undefined, // Applies a multiplicative factor (>0) to expand or compress the overall area that the nodes take up
          condense: false, // uses all available space on false, uses minimal space on true
          rows: undefined, // force num of rows in the grid
          cols: undefined, // force num of columns in the grid
          position: function( node ){}, // returns { row, col } for element
          sort: undefined, // a sorting function to order the nodes; e.g. function(a, b){ return a.data('weight') - b.data('weight') }
          animate: false, // whether to transition the node positions
          animationDuration: 500, // duration of animation in ms if enabled
          animationEasing: undefined, // easing of animation if enabled
          animateFilter: function ( node, i ){ return true; }, // a function that determines whether the node should be animated.  All nodes animated by default on animate enabled.  Non-animated nodes are positioned immediately when the layout starts
          ready: undefined, // callback on layoutready
          stop: undefined, // callback on layoutstop
          transform: function (node, position ){ return position; } // transform a given node position. Useful for changing flow direction in discrete layouts
        };

    let circle_layout = {
          name: 'circle',

          fit: true, // whether to fit the viewport to the graph
          padding: 30, // the padding on fit
          boundingBox: undefined, // constrain layout bounds; { x1, y1, x2, y2 } or { x1, y1, w, h }
          avoidOverlap: true, // prevents node overlap, may overflow boundingBox and radius if not enough space
          nodeDimensionsIncludeLabels: false, // Excludes the label when calculating node bounding boxes for the layout algorithm
          spacingFactor: undefined, // Applies a multiplicative factor (>0) to expand or compress the overall area that the nodes take up
          radius: undefined, // the radius of the circle
          startAngle: 3 / 2 * Math.PI, // where nodes start in radians
          sweep: undefined, // how many radians should be between the first and last node (defaults to full circle)
          clockwise: true, // whether the layout should go clockwise (true) or counterclockwise/anticlockwise (false)
          sort: undefined, // a sorting function to order the nodes; e.g. function(a, b){ return a.data('weight') - b.data('weight') }
          animate: false, // whether to transition the node positions
          animationDuration: 500, // duration of animation in ms if enabled
          animationEasing: undefined, // easing of animation if enabled
          animateFilter: function ( node, i ){ return true; }, // a function that determines whether the node should be animated.  All nodes animated by default on animate enabled.  Non-animated nodes are positioned immediately when the layout starts
          ready: undefined, // callback on layoutready
          stop: undefined, // callback on layoutstop
          transform: function (node, position ){ return position; } // transform a given node position. Useful for changing flow direction in discrete layouts

        };

    let concentric_layout = {
          name: 'concentric',

          fit: true, // whether to fit the viewport to the graph
          padding: 30, // the padding on fit
          startAngle: 3 / 2 * Math.PI, // where nodes start in radians
          sweep: undefined, // how many radians should be between the first and last node (defaults to full circle)
          clockwise: true, // whether the layout should go clockwise (true) or counterclockwise/anticlockwise (false)
          equidistant: false, // whether levels have an equal radial distance betwen them, may cause bounding box overflow
          minNodeSpacing: 10, // min spacing between outside of nodes (used for radius adjustment)
          boundingBox: undefined, // constrain layout bounds; { x1, y1, x2, y2 } or { x1, y1, w, h }
          avoidOverlap: true, // prevents node overlap, may overflow boundingBox if not enough space
          nodeDimensionsIncludeLabels: false, // Excludes the label when calculating node bounding boxes for the layout algorithm
          height: undefined, // height of layout area (overrides container height)
          width: undefined, // width of layout area (overrides container width)
          spacingFactor: undefined, // Applies a multiplicative factor (>0) to expand or compress the overall area that the nodes take up
          concentric: function( node ){ // returns numeric value for each node, placing higher nodes in levels towards the centre
          return node.degree();
          },
          levelWidth: function( nodes ){ // the letiation of concentric values in each level
          return nodes.maxDegree() / 4;
          },
          animate: false, // whether to transition the node positions
          animationDuration: 500, // duration of animation in ms if enabled
          animationEasing: undefined, // easing of animation if enabled
          animateFilter: function ( node, i ){ return true; }, // a function that determines whether the node should be animated.  All nodes animated by default on animate enabled.  Non-animated nodes are positioned immediately when the layout starts
          ready: undefined, // callback on layoutready
          stop: undefined, // callback on layoutstop
          transform: function (node, position ){ return position; } // transform a given node position. Useful for changing flow direction in discrete layouts
        };

    let breadthfirst_layout = {
          name: 'breadthfirst',

          fit: true, // whether to fit the viewport to the graph
          directed: false, // whether the tree is directed downwards (or edges can point in any direction if false)
          padding: 30, // padding on fit
          circle: false, // put depths in concentric circles if true, put depths top down if false
          grid: false, // whether to create an even grid into which the DAG is placed (circle:false only)
          spacingFactor: 1.75, // positive spacing factor, larger => more space between nodes (N.B. n/a if causes overlap)
          boundingBox: undefined, // constrain layout bounds; { x1, y1, x2, y2 } or { x1, y1, w, h }
          avoidOverlap: true, // prevents node overlap, may overflow boundingBox if not enough space
          nodeDimensionsIncludeLabels: false, // Excludes the label when calculating node bounding boxes for the layout algorithm
          roots: undefined, // the roots of the trees
          maximal: false, // whether to shift nodes down their natural BFS depths in order to avoid upwards edges (DAGS only)
          animate: false, // whether to transition the node positions
          animationDuration: 500, // duration of animation in ms if enabled
          animationEasing: undefined, // easing of animation if enabled,
          animateFilter: function ( node, i ){ return true; }, // a function that determines whether the node should be animated.  All nodes animated by default on animate enabled.  Non-animated nodes are positioned immediately when the layout starts
          ready: undefined, // callback on layoutready
          stop: undefined, // callback on layoutstop
          transform: function (node, position ){ return position; } // transform a given node position. Useful for changing flow direction in discrete layouts
        };



    switch (option){
        case 'Cose':
            return cose_layout;
        case 'Random':
            return random_layout;
        case 'Preset':
            return preset_layout;
        case 'Grid':
            return grid_layout;
        case 'Circle':
            return circle_layout;
        case 'Concentric':
            return concentric_layout;
        case 'Breadthfirst':
            return breadthfirst_layout;
    }
    return random_layout;
}