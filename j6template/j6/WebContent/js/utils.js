
function showLoading(){
	Richfaces.showModalPanel('pvsLoadingDialog');
}

function hideLoading(){
	Richfaces.hideModalPanel('pvsLoadingDialog');
}

function confirmationToolBar(){
	var operation = document.getElementById('actions').value;
	
	if(operation=='Delete' || operation=='Set In-Active' || operation=='Cancel'){
	
		var answer= confirm("Are you sure you want to perform "+operation+" operation?");
		if (answer== true){
			return true;
		}else{
			return false;
		}
	}
	return true;
}

function confirmation(operation){
		
	var answer= confirm("Are you sure you want to "+operation+" ?");
	if (answer== true){
		return true;
	}else{
		return false;
	}
	
	return true;
}

function textCounter(field, countfield, maxlimit) {
	if (field.value.length > maxlimit) // if too long...trim it!
		field.value = field.value.substring(0, maxlimit);
	// otherwise, update 'characters left' counter
	else 
		countfield.innerHTML = maxlimit - field.value.length;
}

function isErrorExisted(jsfFormId){
	var isErrorExisted = false;
	var form;
	if (jsfFormId!=''){
		form = document.getElementById(jsfFormId);	
		errors = form.getElementsByTagName('span');
	
		for (  i=0 ; i < errors.length ; i++ ) {	
			if (errors[i].className=="error")	
				isErrorExisted= true;
		}
	}
	
	form = document.getElementById('errorMessages');	
	if (form !=null){
		errors = form.getElementsByTagName('span');
	
		for (  i=0 ; i < errors.length ; i++ ) {	
			if (errors[i].className=="errorMessage")	
				isErrorExisted= true;
			//TODO hardcode date format here	
			else if (error[i].innerHtml=="Date format is dd/MM/yyyy.")
				isErrorExisted= true;					
		}
	}
	return isErrorExisted;
}

function checkAll(form,checkAllObject) {
	
	var chkAll = checkAllObject.checked;
	
	for (  i=0 ; i < form.elements.length ; i++ ) {			
		if ( form.elements[i].type == "checkbox" ){
			form.elements[i].checked = chkAll;
		}
	}
}		

function getPrefix( htmlId ){
	var prefixSplitted = htmlId.split(':');
	var prefix = prefixSplitted[0];

	for(i=1;i<prefixSplitted.length-1;i++){
		prefix = prefix + ":" + prefixSplitted[i];
	}
	return prefix;
}

function clearJsfForm(jsfFormId) {
	
	var form = document.getElementById(jsfFormId);	
	errors = form.getElementsByTagName('span');
	
	for (  i=0 ; i < errors.length ; i++ ) {	
		if (errors[i].className=="error")	
			errors[i].parentNode.removeChild(errors[i]);		
	}
	
	for (  i=0 ; i < form.elements.length ; i++ ) {		
		if ( form.elements[i].type == "textarea" || form.elements[i].type == "text" || form.elements[i].type == "select-one" ){
			form.elements[i].value = "";
		}
	}
	
}		

//id is the id of SelectOneRadioValue
//get tne selected value of jsf's SelectOneRadio
function getSelectOneRadioValue(id){
	collection = document.getElementsByName(id);
	for(i=0;i<collection.length;i++){
		if (collection[i].checked==true){
				
			return collection[i].value;
		}
	}
}

function printReport(formElementId)
{
	var gAutoPrint = false; // Flag for whether or not to automatically call the print function
	
	if (document.getElementById != null)
	{
		var html = '<HTML>\n<HEAD>\n';

		if (document.getElementsByTagName != null)
		{
			var headTags = document.getElementsByTagName("head");
			if (headTags.length > 0)
				html += headTags[0].innerHTML;
		}
		
		html += '\n</HE' + 'AD>\n<BODY>\n';
		
		var printReadyElem = document.getElementById(formElementId);
		
		if (printReadyElem != null)
		{
				html += printReadyElem.innerHTML;
		}
		else
		{
			alert("Could not find the printReady section in the HTML");
			return;
		}
			
		html += '\n</BO' + 'DY>\n</HT' + 'ML>';
		
	    var iMyWidth = window.screen.width - 10;
	    var iMyHeight = window.screen.height - 150;
	    var left = 0;//(window.screen.width/2) - (iMyWidth/2 + 10);
	    var top = 0;//(window.screen.height/2) - (iMyHeight/2 + 50);

	    var parameter = "addressbar=yes,toolbar=yes,menubar=yes,scrollbars=yes,navigationbars=yes,height=" + iMyHeight + ",width=" + iMyWidth + ",left=" + left + ",top=" + top;
		
		var printWin = window.open("","printSpecial",parameter);
		printWin.document.open();
		printWin.document.write(html);
		printWin.document.close();
		if (gAutoPrint)
			printWin.print();
	}
	else
	{
		alert("Sorry, the print ready feature is only available in modern browsers.");
	}
}