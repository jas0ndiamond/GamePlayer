$( document ).ready(
		function() 
        { 
          //grab the first view
          updateViews();
          
          //passive check for updates
          //must be in a function
          setInterval(  function(){updateViews();} , 1000);
         }
);


function updateViews()
{
	$.ajax
	({
        type:"get",
        url:"/golstate",
        dataType : 'json'
	})
	.success(function(data)
    {
		//get the new state, and render it in the div
			
		$.each(data, function(key, value) 
		{
		

		
			$('#gameheader' + key).html
			(
				"Title: " + key + "<br>" + 
				"Turn: " + value.turn
			);
			
			var renderedWorld = buildRenderedWorld(value.world);
			
			$('#gameworld' + key).html
			(
				renderedWorld
			);
		});	
		
		
    }).error (function()
    {

	});
}

function buildRenderedWorld(worldArray)
{
	var retval = "";
	var square;
	var value;
	
	for(var i = 0; i < worldArray.length; i++)
	{
		for(var j = 0; j <worldArray[i].length; j++)
		{
			//retval += value;
		
			square = "<canvas width='20' height='20' style='border:1px solid black; background-color: ";
		
			value = worldArray[i][j] ;
			
			switch(value){
				case "0": square += "#FFF"; break;
				case "1": square += "#00F"; break;
				case "2": square += "#0F0"; break;
				case "3": square += "#F00"; break;
				case "4": square += "#000"; break;
				default: square += value; break;
			}
			
			retval += square + ";'></canvas>";

		}
		retval += "<br>";
		
	}
	
	return retval;
}
