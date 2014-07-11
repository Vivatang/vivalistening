var arrayId = new Array();
var current_id = null;
var play_id = 105;
var bTranslation = true;



function addParagraph(paras){
	
	var parent = $("<div></div>");
	parent.addClass('listening-paragraph-div');
	for(var i = 0; i < paras.length ; i++){
		addSentense(paras[i],parent);
	}
	
	parent.appendTo("body");
	
};

function buildText(text){
	for(var i = 0 ; i < text.length ; i++){
		addParagraph(text[i]);
	}
};

function addSentense(par,parent){
	var label = $("<label></label>");
	label.attr('id',par.time);
	arrayId.push(par.time);
	label.click(function(){
		var id = $(this).attr('id');
		select_current_sentense(id);
		window.activity.setPosition(id);
	});	
	
	
	label.html(par.text);
	label.appendTo(parent);
	
	if(par.translation != null){
		var p = $("<p></p>");
		p.html(par.translation);
		p.appendTo(parent);
		
	}
};

function addTranslation(par,parent){
	
};

function getIdByTime(time){
	for(var i = 0 ; i< arrayId.length ; i++){
		if(time >= arrayId[i]){
			if(i == arrayId.length - 1 || time < arrayId[i+1]){
				return arrayId[i];
			}
		}
	}
};

function select_current_sentense (id){
	if(id == current_id){
		return;
	}
	
	if(current_id != null){
		$("#"+current_id).removeClass('listening-selected');
	}
	
	$("#"+id).addClass('listening-selected');
	current_id = id;
	scroll(id);
};

function set_current_pos(time){
	
	var id = getIdByTime(time);
	select_current_sentense(id);
};

function scroll(id){
	var el = $("#"+id);
	var clientHeight = $(window).height(); 
	var clientBottom = $(document.body).scrollTop() + clientHeight;
	var top = el.offset().top;
	var bottom = top + el.height();
	if(bottom > clientBottom || top <  $(document.body).scrollTop() ){
		var pos = top  - clientHeight/3;
		if(pos < 0){
			pos = 0;
		}
		$(document.body).scrollTop(pos);
	}
	else
	{
		//after the body is at the end , error will happen if user restart.It will move to the end!why? 
		var pos = $(document.body).scrollTop();
		$(document.body).scrollTop(pos);	
	}
		
};

function showTranslation(){
	if(bTranslation == true){
		$("p").hide();
	}
	else{
		$("p").show();
	}
	bTranslation = !bTranslation;	
};
