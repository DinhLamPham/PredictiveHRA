//
function FormatByDC(){
    cy.nodes().forEach(nodeEle =>{
        let dcVal = GetDegreeCentrality(nodeEle)
        let nodeSize = Math.floor(minNodeSize + dcVal*(maxNodeSize-minNodeSize))
        let colorCode = perc2color(dcVal*100)
        SetNodeColor(nodeEle, colorCode)

        // console.log(nodeEle._private.data['id'], 'degree: ', dcVal, 'size: ', nodeSize)
        SetNodeSize(nodeEle, nodeSize)

    })

}


function FormatByBC(){
    // Only highlight the max BC Value
    let bcArray = {}, minVal = 100, maxVal = -1
    cy.nodes().forEach(nodeEle =>{
        let bcVal = GetBetweennessCentrality(nodeEle)
        if(minVal>bcVal) minVal=bcVal
        if(maxVal<bcVal) maxVal=bcVal

        bcArray[nodeEle._private.data['id']] = bcVal
    })


    // Min max scalling bcArray
    for(let nodeId in bcArray){
        let bcVal = bcArray[nodeId]
        let newBCVal = (bcVal - minVal)/(maxVal - minVal)  // Scal newBC to (0, 1) range
        let nodeSize = Math.floor(minNodeSize + newBCVal*(maxNodeSize-minNodeSize))
        SetNodeSize(cy.$id(nodeId), nodeSize)

        let colorCode = perc2color(newBCVal*100)
        SetNodeColor(cy.$id(nodeId), colorCode)

    }


    // Set nodeSize.

}

function FormatByCC(){
    cy.nodes().forEach(nodeEle =>{
        let ccVal = GetClosenessCentrality(nodeEle)
        let nodeSize = Math.floor(minNodeSize + ccVal*(maxNodeSize-minNodeSize))

        let colorCode = perc2color(ccVal*100)
        SetNodeColor(nodeEle, colorCode)

        console.log(nodeEle._private.data['id'], 'degree: ', ccVal, 'size: ', nodeSize)
        SetNodeSize(nodeEle, nodeSize)
    })


}