var cy = window.cy = cytoscape({
					container: document.getElementById('cy1'),

					layout: {
						name: 'springy'
					},

					style: [
						{
							selector: 'node',
							css: {
								'content': 'data(name)'
							}
						},

						{
							selector: 'edge',
							css: {
								'target-arrow-shape': 'triangle'
							}
						}
					],

					elements: {
						nodes: [
							{ data: { id: 'j', name: 'Jerry' } },
							{ data: { id: 'e', name: 'Elaine' } },
							{ data: { id: 'k', name: 'Kramer' } },
							{ data: { id: 'g', name: 'George' } }
						],
						edges: [
							{ data: { source: 'j', target: 'e' } },
							{ data: { source: 'j', target: 'k' } },
							{ data: { source: 'j', target: 'g' } },
							{ data: { source: 'e', target: 'j' } },
							{ data: { source: 'e', target: 'k' } },
							{ data: { source: 'k', target: 'j' } },
							{ data: { source: 'k', target: 'e' } },
							{ data: { source: 'k', target: 'g' } },
							{ data: { source: 'g', target: 'j' } }
						]
					}
				});