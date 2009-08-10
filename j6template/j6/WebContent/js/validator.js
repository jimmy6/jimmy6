
// allow field type

var INTEGER = 100;
var NUMERIC = 101;
var DOUBLE = 200;
var STRING = 300;
var DATE = 400;
var SELECT = 500;
var RADIO = 600;
var CHECK = 700;
var EMAIL = 800;
var CUSTOMED = 900;
var MULTIPLE = 1000;
var DATE_ALLOWED_PAST = 411;
var DATE_ALLOWED_FUTURE = 412;
var DATE_ALLOWED_ALL = 413;

var IS_CURRENT_DOCUMENT_SUBMITTED = false ;

// declaring the object.

// customed validator
function CustomType(customedMsg,customMethod) {		
	this.dataType = CUSTOMED;	
	this.customedMsg = customedMsg;	
	this.customMethod = customMethod;	
}

// check box validator
function CheckType(formElement, prettyName, customedMsg) {	
	this.formElement = formElement;
	this.dataType = CHECK;	
	this.mandatory = true;		
	this.prettyName = prettyName;
	this.customedMsg = customedMsg;	
}

// select box validator
function SelectType(formElement, prettyName, customedMsg) {	
	this.formElement = formElement;
	this.dataType = SELECT;	
	this.mandatory = true;		
	this.prettyName = prettyName;
	this.customedMsg = customedMsg;	
}

// radio validator
function RadioType(formElement, prettyName, customedMsg) {	
	this.formElement = formElement;
	this.dataType = RADIO;	
	this.mandatory = true;		
	this.prettyName = prettyName;
	this.customedMsg = customedMsg;	
}

//string validator
function StringType(formElement, mandatory, charsNot, prettyName, customedMsg) {	
	this.formElement = formElement;
	this.dataType = STRING;	
	this.mandatory = mandatory;		
	this.prettyName = prettyName;
	this.customedMsg = customedMsg;	
	this.charsNot = charsNot;
}

// multiple string validator
function MultipleType(formElement, mandatory, charsNot, prettyName, customedMsg) {	
	this.formElement = formElement;
	this.dataType = MULTIPLE;	
	this.mandatory = mandatory;		
	this.prettyName = prettyName;
	this.customedMsg = customedMsg;	
	this.charsNot = charsNot;
}

//date validator
function DateType(formElement, mandatory, prettyName, customedMsg) {	
	this.formElement = formElement;
	this.dataType = DATE;	
	this.mandatory = mandatory;		
	this.prettyName = prettyName;
	this.customedMsg = customedMsg;	
	this.allowedDate = DATE_ALLOWED_ALL;
}

//integer validator
function IntegerType(formElement, mandatory, prettyName, customedMsg) {	
	this.formElement = formElement;
	this.dataType = INTEGER;
	this.mandatory = mandatory;			
	this.prettyName = prettyName;
	this.customedMsg = customedMsg;
}

//numeric validator
function NumericType(formElement, mandatory, prettyName, customedMsg) {	
	this.formElement = formElement;
	this.dataType = NUMERIC;
	this.mandatory = mandatory;			
	this.prettyName = prettyName;
	this.customedMsg = customedMsg;
}

// double validator
function DoubleType(formElement, mandatory, decimalPlace, prettyName, customedMsg) {	
	this.formElement = formElement;
	this.dataType = DOUBLE;
	this.mandatory = mandatory;		
	this.decimalPlace = decimalPlace;
	this.prettyName = prettyName;
	this.customedMsg = customedMsg;
}

// has form object with specific name

function hasFormElement(formElement,elementType,elementName) {
	
	var hasElement=false ;
	
	for (  i=0 ; i < formElement.elements.length ; i++ ) {			
		
		if (( formElement.elements[i].type == elementType ) && 
			( formElement.elements[i].name == elementName ) ) {
			hasElement=true ;
			break;
		}			
	}		
	
	return hasElement;
	
}

function verify(validatorArray) {

	var errorMesg = "";
	
	var arrayLength = validatorArray.length;

	for(i=0;i<validatorArray.length;i++) {
	
		var validator = validatorArray[i];			

		 
		//////////////////////////////////
		// validate integer
		if (validator.dataType == INTEGER) {
			
			if (validator.mandatory) {			
				if (!verifyNotBlank(validator.formElement)) {
					
					if (validator.prettyName != "")	 {
						errorMesg+=validator.prettyName+" is required.\n";	
					}else{					
						errorMesg+=validator.customedMsg+"\n";					
					}
				}		
			} 
			
			if (!verifyInteger(validator.formElement)) {

				if (validator.prettyName != "")	 {
					errorMesg+=validator.prettyName+" must be an integer.\n"	;
				}else{
					errorMesg+=validator.customedMsg+"\n";
				}				
			}

		//////////////////////////////////
		}else if (validator.dataType == MULTIPLE) {			
			var isValidated = false;	
			if (validator.mandatory) {			
				if (!verifyMultipleNotBlank(validator.formElement)) {
					isValidated = true;					
					if (validator.prettyName != "")	 {
						errorMesg+=validator.prettyName+" is required.\n";	
					}else{					
						errorMesg+=validator.customedMsg+"\n";					
					}	
				}		
			} 
		//////////////////////////////////		
		
		// validate numeric
        } else if (validator.dataType == NUMERIC) {
			
			if (validator.mandatory) {			
				if (!verifyNotBlank(validator.formElement)) {
					
					if (validator.prettyName != "")	 {
						errorMesg+=validator.prettyName+" is required.\n";	
					}else{					
						errorMesg+=validator.customedMsg+"\n";					
					}
				}		
			} 
			
			if (!verifyNumeric(validator.formElement)) {

				if (validator.prettyName != "")	 {
					errorMesg+=validator.prettyName+" must be a numeric.\n"	;
				}else{
					errorMesg+=validator.customedMsg+"\n";
				}				
			}	
			
			
		//////////////////////////////////
		// validate double			
		} else if (validator.dataType == DOUBLE) {
			
			if (validator.mandatory) {			
				if (!verifyNotBlank(validator.formElement)) {
					
					if (validator.prettyName != "")	 {
						errorMesg+=validator.prettyName+" is required.\n";	
					}else{					
						errorMesg+=validator.customedMsg+"\n";					
					}					
				}		
			} 
			
			if (!verifyDouble(validator.formElement,validator.decimalPlace)) {
				
				if (validator.prettyName != "")	 {
					errorMesg+=validator.prettyName+" must be floating number with "+validator.decimalPlace+" decimal places.\n";	
				}else{					
					errorMesg+=validator.customedMsg+"\n";					
				}				
			}		
				
		//////////////////////////////////
		// validate string			
		} else if (validator.dataType == STRING) {			
			var isValidated = false;	
			
			if (validator.mandatory) {			
				if (!verifyNotBlank(validator.formElement)) {
					isValidated = true;					
					if (validator.prettyName != "")	 {
						errorMesg+=validator.prettyName+" is required.\n";	
					}else{					
						errorMesg+=validator.customedMsg+"\n";					
					}	
				}		
			} 
			
			var stringValue = validator.formElement.value;
			
			if (!isValidated) {
				if (validator.charsNot != "") {							
					for(n=0;n<stringValue.length;n++){												
						if (validator.charsNot.indexOf(stringValue.substring(n,n+1))>-1) {							
							errorMesg+=validator.prettyName+" can not contain \""+validator.charsNot+"\"\n";
							break;	
						}						
					}					
				}
			}
			
		
		//////////////////////////////////
		// validate date			
		} else if (validator.dataType == DATE) {
			
			var isValid = true;
			
			if (validator.mandatory) {							
				
				if (!verifyNotBlank(validator.formElement)) {	
					if (validator.prettyName != "")	 {
						errorMesg+=validator.prettyName+" is required.\n";	
					}else{					
						errorMesg+=validator.customedMsg+"\n";					
					} 					
				} else if (!verifyDate(validator.formElement.value)) {
					if (validator.prettyName != "")	 {
						errorMesg+=validator.prettyName+" is not in the correct format.\n";	
					}else{					
						errorMesg+=validator.customedMsg+"\n";					
					} 
				}		
			} else {
			
				if (validator.formElement.value!=""){
				
					if (!verifyDate(validator.formElement.value)) {
						if (validator.prettyName != "")	 {
							errorMesg+=validator.prettyName+" is not in the correct format.\n";	
						}else{					
							errorMesg+=validator.customedMsg+"\n";					
						} 
					}	
				}
			
			}	
				
		//////////////////////////////////
		// validate select			
		} else if (validator.dataType == SELECT) {
			
			if (validator.mandatory) {			
				if (!verifySelect(validator.formElement)) {
					
					if (validator.prettyName != "")	 {
						errorMesg+=validator.prettyName+" is required.\n";	
					}else{					
						errorMesg+=validator.customedMsg+"\n";					
					}					
				}		
			} 			

		//////////////////////////////////
		// validate radio			
		} else if (validator.dataType == RADIO) {

			if (validator.mandatory) {			
				if (!verifyRadio(validator.formElement)) {
					if (validator.prettyName != "")	 {
						errorMesg+=validator.prettyName+" is required.\n";	
					}else{					
						errorMesg+=validator.customedMsg+"\n";					
					}	
				}		
			}			
			
		//////////////////////////////////
		// validate check			
		} else if (validator.dataType == CHECK) {

			if (validator.mandatory) {			
				if (!verifyCheck(validator.formElement)) {
					if (validator.prettyName != "")	 {
						errorMesg+=validator.prettyName+" is required.\n";	
					}else{					
						errorMesg+=validator.customedMsg+"\n";					
					}	
				}		
			}				
			
		//////////////////////////////////
		// validate email			
		} else if (validator.dataType == EMAIL) {
			
			
		//////////////////////////////////
		// validate custom			
		} else if (validator.dataType == CUSTOMED) {			
			
			var result = eval(validator.customMethod);
			
			if (!result) {
				errorMesg+=validator.customedMsg+"\n";
			}
			
		
					
		} else {
			
			
			
		}		
		
	}
	
	if (errorMesg != "") {
		
		errorMesg = "The submission is not successful.                \n"+
					"---------------------------------------\n"+
					"Please correct the following errors:-\n\n"+errorMesg;
		
		alert(errorMesg+"");
	}
	
	
	if (errorMesg != "") {
		return false;	
	} else {
		return true;
	}
	

}	  


///////////////////////////////////////////////////////////////////////////////////
// respective type validator


function verifyInteger(aTextField){
	var textValue = aTextField.value;
		
	if (isNaN(textValue)) {	
		return false;
	} else {
		if (textValue.indexOf(".") > -1) {
			return false
		} else {
			return true;
		}
	}	
}

function verifyNumeric(aTextField){
	var textValue = aTextField.value;
		
	if (isNaN(textValue)|| textValue.indexOf("+") > -1 || textValue.indexOf("-") > -1 || textValue.indexOf(" ") > -1) {	
		return false;
	} else {
		if (textValue.indexOf(".") > -1) {
			return false;
		} else {
			return true;
		}
	}	
}

function verifyDouble(textField,decimalPlace){
	
	var textValue = textField.value;	
	
	var theLen = textValue.length; 
	var dotPos = textValue.indexOf("."); 	
	
	/*This is to cater to no dot value.
	*/
	if (dotPos < 0) {
		dotPos = theLen-1;
	}	
	var thePos = theLen - (dotPos+1);
	var isNum = (isNaN(textValue)) ? false:true; 	
	var isAt = (thePos <= decimalPlace) ? true:false; 	
	var isOne = (textValue.indexOf(".",dotPos+1) == -1) ? true:false; 	
	
	if((isNum) && (isAt) && (isOne)) { 
		return true;	
	} else { 
		return false;	
	} 
}

function verifyNumericDecimal(textField,decimalPlace){
	
	var textValue = textField.value;	
	
	var theLen = textValue.length; 
	var dotPos = textValue.indexOf("."); 	
	
	//This is to cater to no dot value.
	if (dotPos <= 0) {
		dotPos = theLen-1;
	}	
	var thePos = theLen - (dotPos+1);
	var isNum = (isNaN(textValue)) ? false:true; 	
	var isAt = (thePos <= decimalPlace) ? true:false; 	
	var isOne = (textValue.indexOf(".",dotPos+1) == -1) ? true:false; 	
	
	if((isNum) && (isAt) && (isOne)) { 
		return true;	
	} else { 
		return false;	
	} 
}


function verifyNotBlank(textField) {

	var textValue = trimText(textField.value);
	
	if (textValue == "") {
		return false;	
	} else {
		return true;
	}	
	
}

function verifySelect(textField) {

	var textValue = textField.value;
	
	if (textValue == "") {
		return false;	
	} else {
		return true;
	}
	
}


function verifyRadio(radioField) {
	
	for (c=0;c<radioField.length;c++){		
		if(radioField[c].checked){
			return true;
		}		
	}	
	return false;
}

function verifyCheck(checkField) {

	if (checkField.length == null) {
		if (checkField.checked) {
			return true;	
		}		
	} else {
		
		for (c=0;c<checkField.length;c++){		
			if(checkField[c].checked){
				return true;
			}		
		}	
		
	}
	return false;
}

function verifyDate(dateField) {
	//RT-LOG #1 Bug Fixed by Jacky 11-OCT-2005 1624
	var dateArray = new Array();
	dateArray = dateField.split('/');
	var aDay="";
	var aMonth="";
	var aYear="";
	
	for (var i=0;i<dateArray.length;i++){
		if (i==0)
		   aDay = dateArray[i];
		
		if (i==1)
		   aMonth = dateArray[i];   
		
		if (i==2)
		   aYear = dateArray[i];   
	}
	
	//var aDay = dateField.substring(0,2);
	//var aMonth = dateField.substring(3,5);	
	//var aYear = dateField.substring(6);
	
	//if(dateField.length!=10){
	//	return false;
	//}
	
	if (aDay.indexOf("0") == 0 ) {
		aDay = aDay.substring(1);
	}
	
	if (aMonth.indexOf("0") == 0 ) {
		aMonth = aMonth.substring(1);
	}
	
	intDay=parseInt(aDay);
	intMonth=parseInt(aMonth);
	intYear=parseInt(aYear);
	
	var aDate = new Date(intYear,intMonth-1,intDay);
	
	
	if ( ( aDate.getMonth() == intMonth-1 ) && ( aDate.getDate() == intDay ) ) {
		return true;
	} else {
		return false;
	}
	
}

function verifyEmailAddress(emailObj){
	var emailStr = new String(emailObj.value);
	var index = emailStr.indexOf("@");
	if( emailStr==null || emailStr=="" )
			return true;
			
	else if (index > 0)
	 {
     	var pindex = emailStr.indexOf(".",index);
    	if ((pindex > index+1) && (emailStr.length > pindex+1))
		return true;
	}
	return false;
}
//Added by jacky 02-Jan-2005 1450
function validatePhoneFaxNumber(formField,alertMsg) 
{
  var phone = formField.value;
  var exp = /^(\+\d{1,3} ?)?(\(\d{1,5}\)|\d{1,5}) ?\d{3,4} ?\d{0,7} ?(x|xtn|ext|extn|extension)??\.? ?\d{1,5}?$/i;
  var isValid = exp.test(phone);

  if( phone==null || phone=="" )
			return true;
			
  if (!isValid) {
    alert(alertMsg);
    formField.focus();
    }
  return isValid;
}    

function trimText(s) 
{
  // Remove leading spaces and carriage returns
  
  while ((s.substring(0,1) == ' ') || (s.substring(0,1) == '\n') || (s.substring(0,1) == '\r'))
  {
    s = s.substring(1,s.length);
  }

  // Remove trailing spaces and carriage returns

  while ((s.substring(s.length-1,s.length) == ' ') || (s.substring(s.length-1,s.length) == '\n') || (s.substring(s.length-1,s.length) == '\r'))
  {
    s = s.substring(0,s.length-1);
  }
  return s;
}

//only deal with positive integer string
function convertToInt(str) {
	var num=0;
	var i=0;
	
	for (i=0; i<str.length; i++) {
		var cChar = str.charAt(i);
		if (cChar>='0' && cChar<='9')
			num = num*10+(cChar-'0');
		else
			return -1;
   	}
   	if (i==0)
   		return -1;
   	else
		return num;		
}

function truncate(num) { 
var str = num + ''; // Now it's a string. 
if (str.indexOf('.') == -1) { return str + '.00'; } 
dot = str.length - str.indexOf('.'); 
if (dot > 3) { return str.substring(0,str.length-dot+3); } 
else if (dot == 2) { return str + '0'; } 
return str; 
} 

function verifyMultipleNotBlank(textField) {
	
	if (textField.value != null) {
		return verifyNotBlank(textField);
	} else {
		var arraySize = textField.length;
		var textValue = "";	
	
		for (co=0;co<arraySize;co++) {
			textValue = textField[co].value;
			if (textValue == "") {
				break;
			} 
		}	
	
		if (textValue == "") {
			return false;
		} else {
			return true;
		}
	}

	
}

function checkDoubleSubmission() {

	if (!IS_CURRENT_DOCUMENT_SUBMITTED) {
		IS_CURRENT_DOCUMENT_SUBMITTED=true;		
		return false;
	} else {
		return true;	
	}

}

function popUp(URL) {  
	day = new Date();
	//id = day.getTime(); 
	id=1;
	eval("page" + id + " = window.open(URL, '" + id + "', 'toolbar=0,scrollbars=1,location=0,statusbar=1,menubar=0,resizable=1,width=650,height=300');");
}


function roundOff(value){

		var floatValue = parseFloat(value);
	   	var roundOffValue = (Math.round(floatValue*100))/100;
	   	
	   	return roundOffValue;
}