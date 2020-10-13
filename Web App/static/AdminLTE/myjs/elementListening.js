// Visualize the trace following the selected type.
$("#visual_type").change(function () {
    let input_layout = this.value;
    let options = Cose_Layout(input_layout);
    let layout = cy.layout( options );
    layout.run();
    // ClickNodeListener();
    });