// chart JavaScript Document

  
  //en scroll
//  $('#scrollbox3').enscroll({
 ///   showOnHover: true,
 //   verticalTrackClass: 'track3',
 //   verticalHandleClass: 'handle3'
//});
  
$('#openBtn').click(function(){
	$('#myModal').modal({show:true})
});

$(function(){
	var winHeight = $(window).height();
	$(".modal-body").css("height", winHeight - 180 );
});
  
