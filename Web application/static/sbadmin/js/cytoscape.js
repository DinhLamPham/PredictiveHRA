var cy = cytoscape({
  container: document.getElementById('cy'), // container to render in
  elements:
  [
    { data: { id: 'a' }},
    { data: { id: 'b' }},
    { data: { id: 'c' }},
    { data: { id: 'd' }},
    { data: { id: 'e' }},
    { data: { id: 'f' }},

    { data: { id: 'ab', source: 'a', target: 'b' }},
    { data: { id: 'cd', source: 'c', target: 'd' }},
    { data: { id: 'da', source: 'd', target: 'a' }},
    { data: { id: 'ea', source: 'e', target: 'a' }},
    { data: { id: 'af', source: 'a', target: 'f' }},
  ],

  style: [ // the stylesheet for the graph
    {
      selector: 'node',
      style: {
        'background-color': '#666',
        'label': 'data(id)'
      }
    },

    {
      selector: 'edge',
      style: {
        'width': 1,
        'line-color': '#ff2245',
        'target-arrow-color': '#ccc',
        'target-arrow-shape': 'triangle',
        'curve-style': 'bezier'
      }
    }
  ],

  layout: {
    name:'cose'
  }

});