
//define all the textfield regex here

//regular expressions

var reUserName =     /^(a-z|A-Z|0-9)*[^#$%^&*()!`~=+{}.,:;@?/\s\-'"<>[\]|\\]*$/;

var reCompName =     /^(a-z|A-Z|0-9)*[^#$%^*!`~=+{},:;?/_"<>[\]|\\]*$/;

var reDeptName =     /^(a-z|A-Z|0-9)*[^#$%^*()'.!`~=+{},:;?/"<>[\]|\\]*$/;

var rePersonName =   /^(a-z|A-Z)*[^\d#$%^&*()!`~=+{},:;?_"<>[\]|\\]*$/;

var reDesc =         /^(a-z|A-Z|0-9)*[^#$%^*()'!`~=+{},:;?_"<>[\]|\\]*$/;

var reAddr =         /^(a-z|A-Z|0-9)*[^#$%^&*()'!`~=+{}:;?_"<>[\]|\\]*$/;

var reCity =         /^(a-z|A-Z)*[^\d#$%^&*()'!`~=+{}.,:;@?/\-_"<>[\]|\\]*$/;

var reDesignation =  /^(a-z|A-Z|0-9)*[^#$%^*()'!`~=+{}.,:;?/"<>[\]|\\]*$/;

var reCompRegNo =    /^(a-z|A-Z|0-9)*[^#$%^&*()'!`~=+{}.,:;@?_"<>[\]|\\]*$/;

var reEmail =        /^(a-z|A-Z|0-9)*[^#$%^&*()'!`~=+{},:;?/"<>[\]|\\]*$/;

var reOldIC =        /^(a-z|A-Z|0-9)*[^#$%^&*()'!`~=+{}.,:;@?/\s\-_"<>[\]|\\]*$/;

var rePhone =        /^(0-9)*[^#$%^&*()'!`~={}(a-z|A-Z).,:;@?/\_"<>[\]|\\]*$/;

var reAlphaNumeric = /^(a-z|A-Z|0-9)*[^#$%^&*()'!`~=+{}.,:;@?/\-_"<>[\]|\\]*$/;

var reNumeric = /^\d*$/;

var reAlphabet = /^\D*$/;






function maskUserNameKeyPress(objEvent) {
  var iKeyCode, strKey, objInput;

  iKeyCode = objEvent.keyCode;
  objInput = objEvent.srcElement;

  strKey = String.fromCharCode(iKeyCode);

  isValid = reUserName.test(objInput.value) || objInput.value.length == 0;

  if ( isValid ) {
    objInput.validValue = objInput.value;
    if (!reUserName.test(strKey)) {
      //alert("Invalid Character Detected!\nKeyCode = " + iKeyCode + "\nCharacter =" + strKey);
      return false;
    }
  }
}
function maskUserNameChange(objEvent) {

  var objInput = objEvent.srcElement;

  var isValid = reUserName.test(objInput.value) || objInput.value.length == 0;

  if ( !isValid ) {
    //alert("Invalid data maskUserNameChange");
    objInput.value = objInput.validValue || "";
    objInput.focus();
    objInput.select();
  }
  else {
    objInput.validValue = objInput.value;
  }
}






function maskCompNameKeyPress(objEvent) {
  var iKeyCode, strKey, objInput;

  iKeyCode = objEvent.keyCode;
  objInput = objEvent.srcElement;

  strKey = String.fromCharCode(iKeyCode);

  isValid = reCompName.test(objInput.value) || objInput.value.length == 0;

  if ( isValid ) {
    objInput.validValue = objInput.value;
    if (!reCompName.test(strKey)) {
      //alert("Invalid Character Detected!\nKeyCode = " + iKeyCode + "\nCharacter =" + strKey);
      return false;
    }
  }
}
function maskCompNameChange(objEvent) {

  var objInput = objEvent.srcElement;

  var isValid = reCompName.test(objInput.value) || objInput.value.length == 0;

  if ( !isValid ) {
    //alert("Invalid data maskCompNameChange");
    objInput.value = objInput.validValue || "";
    objInput.focus();
    objInput.select();
  }
  else {
    objInput.validValue = objInput.value;
  }
}


function maskDeptNameKeyPress(objEvent) {
  var iKeyCode, strKey, objInput;

  iKeyCode = objEvent.keyCode;
  objInput = objEvent.srcElement;

  strKey = String.fromCharCode(iKeyCode);

  isValid = reDeptName.test(objInput.value) || objInput.value.length == 0;

  if ( isValid ) {
    objInput.validValue = objInput.value;
    if (!reDeptName.test(strKey)) {
      //alert("Invalid Character Detected!\nKeyCode = " + iKeyCode + "\nCharacter =" + strKey);
      return false;
    }
  }
}
function maskDeptNameChange(objEvent) {

  var objInput = objEvent.srcElement;

  var isValid = reDeptName.test(objInput.value) || objInput.value.length == 0;

  if ( !isValid ) {
    //alert("Invalid data maskDeptNameChange");
    objInput.value = objInput.validValue || "";
    objInput.focus();
    objInput.select();
  }
  else {
    objInput.validValue = objInput.value;
  }
}












function maskPersonNameKeyPress(objEvent) {
  var iKeyCode, strKey, objInput;

  iKeyCode = objEvent.keyCode;
  objInput = objEvent.srcElement;

  strKey = String.fromCharCode(iKeyCode);

  isValid = rePersonName.test(objInput.value) || objInput.value.length == 0;

  if ( isValid ) {
    objInput.validValue = objInput.value;
    if (!rePersonName.test(strKey)) {
      //alert("Invalid Character Detected!\nKeyCode = " + iKeyCode + "\nCharacter =" + strKey);
      return false;
    }
  }
}
function maskPersonNameChange(objEvent) {

  var objInput = objEvent.srcElement;

  var isValid = rePersonName.test(objInput.value) || objInput.value.length == 0;

  if ( !isValid ) {
    //alert("Invalid data maskPersonNameChange");
    objInput.value = objInput.validValue || "";
    objInput.focus();
    objInput.select();
  }
  else {
    objInput.validValue = objInput.value;
  }
}












function maskDescKeyPress(objEvent) {
  var iKeyCode, strKey, objInput;

  iKeyCode = objEvent.keyCode;
  objInput = objEvent.srcElement;

  strKey = String.fromCharCode(iKeyCode);

  isValid = reDesc.test(objInput.value) || objInput.value.length == 0;

  if ( isValid ) {
    objInput.validValue = objInput.value;
    if (!reDesc.test(strKey)) {
      //alert("Invalid Character Detected!\nKeyCode = " + iKeyCode + "\nCharacter =" + strKey);
      return false;
    }
  }
}
function maskDescChange(objEvent) {

  var objInput = objEvent.srcElement;

  var isValid = reDesc.test(objInput.value) || objInput.value.length == 0;

  if ( !isValid ) {
    //alert("Invalid data maskDescChange");
    objInput.value = objInput.validValue || "";
    objInput.focus();
    objInput.select();
  }
  else {
    objInput.validValue = objInput.value;
  }
}

function maskAddrKeyPress(objEvent) {
  var iKeyCode, strKey, objInput;

  iKeyCode = objEvent.keyCode;
  objInput = objEvent.srcElement;

  strKey = String.fromCharCode(iKeyCode);

  isValid = reAddr.test(objInput.value) || objInput.value.length == 0;

  if ( isValid ) {
    objInput.validValue = objInput.value;
    if (!reAddr.test(strKey)) {
      //alert("Invalid Character Detected!\nKeyCode = " + iKeyCode + "\nCharacter =" + strKey);
      return false;
    }
  }
}

function maskAddrChange(objEvent) {

  var objInput = objEvent.srcElement;

  var isValid = reAddr.test(objInput.value) || objInput.value.length == 0;

  if ( !isValid ) {
    //alert("Invalid data maskDescChange");
    objInput.value = objInput.validValue || "";
    objInput.focus();
    objInput.select();
  }
  else {
    objInput.validValue = objInput.value;
  }
}


function maskCityKeyPress(objEvent) {
  var iKeyCode, strKey, objInput;

  iKeyCode = objEvent.keyCode;
  objInput = objEvent.srcElement;

  strKey = String.fromCharCode(iKeyCode);

  isValid = reCity.test(objInput.value) || objInput.value.length == 0;

  if ( isValid ) {
    objInput.validValue = objInput.value;
    if (!reCity.test(strKey)) {
      //alert("Invalid Character Detected!\nKeyCode = " + iKeyCode + "\nCharacter =" + strKey);
      return false;
    }
  }
}
function maskCityChange(objEvent) {

  var objInput = objEvent.srcElement;

  var isValid = reCity.test(objInput.value) || objInput.value.length == 0;

  if ( !isValid ) {
    //alert("Invalid data maskCity");
    objInput.value = objInput.validValue || "";
    objInput.focus();
    objInput.select();
  }
  else {
    objInput.validValue = objInput.value;
  }
}















function maskDesignationKeyPress(objEvent) {
  var iKeyCode, strKey, objInput;

  iKeyCode = objEvent.keyCode;
  objInput = objEvent.srcElement;

  strKey = String.fromCharCode(iKeyCode);

  isValid = reDesignation.test(objInput.value) || objInput.value.length == 0;

  if ( isValid ) {
    objInput.validValue = objInput.value;
    if (!reDesignation.test(strKey)) {
      //alert("Invalid Character Detected!\nKeyCode = " + iKeyCode + "\nCharacter =" + strKey);
      return false;
    }
  }
}
function maskDesignationChange(objEvent) {

  var objInput = objEvent.srcElement;

  var isValid = reDesignation.test(objInput.value) || objInput.value.length == 0;

  if ( !isValid ) {
    //alert("Invalid data maskDesignationChange");
    objInput.value = objInput.validValue || "";
    objInput.focus();
    objInput.select();
  }
  else {
    objInput.validValue = objInput.value;
  }
}









function maskCompRegNoKeyPress(objEvent) {
  var iKeyCode, strKey, objInput;

  iKeyCode = objEvent.keyCode;
  objInput = objEvent.srcElement;

  strKey = String.fromCharCode(iKeyCode);

  isValid = reCompRegNo.test(objInput.value) || objInput.value.length == 0;

  if ( isValid ) {
    objInput.validValue = objInput.value;
    if (!reCompRegNo.test(strKey)) {
      //alert("Invalid Character Detected!\nKeyCode = " + iKeyCode + "\nCharacter =" + strKey);
      return false;
    }
  }
}
function maskCompRegNoChange(objEvent) {

  var objInput = objEvent.srcElement;

  var isValid = reCompRegNo.test(objInput.value) || objInput.value.length == 0;

  if ( !isValid ) {
    //alert("Invalid data maskCompRegNoChange");
    objInput.value = objInput.validValue || "";
    objInput.focus();
    objInput.select();
  }
  else {
    objInput.validValue = objInput.value;
  }
}







function maskEmailKeyPress(objEvent) {
  var iKeyCode, strKey, objInput;

  iKeyCode = objEvent.keyCode;
  objInput = objEvent.srcElement;

  strKey = String.fromCharCode(iKeyCode);

  isValid = reEmail.test(objInput.value) || objInput.value.length == 0;

  if ( isValid ) {
    objInput.validValue = objInput.value;
    if (!reEmail.test(strKey)) {
      //alert("Invalid Character Detected!\nKeyCode = " + iKeyCode + "\nCharacter =" + strKey);
      return false;
    }
  }
}
function maskEmailChange(objEvent) {

  var objInput = objEvent.srcElement;

  var isValid = reEmail.test(objInput.value) || objInput.value.length == 0;

  if ( !isValid ) {
    //alert("Invalid data maskEmailChange");
    objInput.value = objInput.validValue || "";
    objInput.focus();
    objInput.select();
  }
  else {
    objInput.validValue = objInput.value;
  }
}













function maskAlphaNumericKeyPress(objEvent) {
  var iKeyCode, strKey, objInput;

  iKeyCode = objEvent.keyCode;
  objInput = objEvent.srcElement;

  strKey = String.fromCharCode(iKeyCode);

  isValid = reAlphaNumeric.test(objInput.value) || objInput.value.length == 0;

  if ( isValid ) {
    objInput.validValue = objInput.value;
    if (!reAlphaNumeric.test(strKey)) {
      //alert("Invalid Character Detected!\nKeyCode = " + iKeyCode + "\nCharacter =" + strKey);
      return false;
    }
  }
}
function maskAlphaNumericChange(objEvent) {

  var objInput = objEvent.srcElement;

  var isValid = reAlphaNumeric.test(objInput.value) || objInput.value.length == 0;

  if ( !isValid ) {
    //alert("Invalid data maskAlphaNumericChange");
    objInput.value = objInput.validValue || "";
    objInput.focus();
    objInput.select();
  }
  else {
    objInput.validValue = objInput.value;
  }
}







function maskOldICKeyPress(objEvent) {
  var iKeyCode, strKey, objInput;

  iKeyCode = objEvent.keyCode;
  objInput = objEvent.srcElement;

  strKey = String.fromCharCode(iKeyCode);

  isValid = reOldIC.test(objInput.value) || objInput.value.length == 0;

  if ( isValid ) {
    objInput.validValue = objInput.value;
    if (!reOldIC.test(strKey)) {
      //alert("Invalid Character Detected!\nKeyCode = " + iKeyCode + "\nCharacter =" + strKey);
      return false;
    }
  }
}
function maskOldICChange(objEvent) {

  var objInput = objEvent.srcElement;

  var isValid = reOldIC.test(objInput.value) || objInput.value.length == 0;

  if ( !isValid ) {
    //alert("Invalid data maskNumericChange");
    objInput.value = objInput.validValue || "";
    objInput.focus();
    objInput.select();
  }
  else {
    objInput.validValue = objInput.value;
  }
}

function maskPhoneKeyPress(objEvent) {
  var iKeyCode, strKey, objInput;

  iKeyCode = objEvent.keyCode;
  objInput = objEvent.srcElement;

  strKey = String.fromCharCode(iKeyCode);

  isValid = rePhone.test(objInput.value) || objInput.value.length == 0;

  if ( isValid ) {
    objInput.validValue = objInput.value;
    if (!rePhone.test(strKey)) {
      //alert("Invalid Character Detected!\nKeyCode = " + iKeyCode + "\nCharacter =" + strKey);
      return false;
    }
  }
}
function maskPhoneChange(objEvent) {

  var objInput = objEvent.srcElement;

  var isValid = rePhone.test(objInput.value) || objInput.value.length == 0;

  if ( !isValid ) {
    //alert("Invalid data maskPhoneChange");
    objInput.value = objInput.validValue || "";
    objInput.focus();
    objInput.select();
  }
  else {
    objInput.validValue = objInput.value;
  }
}



function maskNumericKeyPress(objEvent) {
  var iKeyCode, strKey, objInput;

  iKeyCode = objEvent.keyCode;
  objInput = objEvent.srcElement;

  strKey = String.fromCharCode(iKeyCode);

  isValid = reNumeric.test(objInput.value) || objInput.value.length == 0;

  if ( isValid ) {
    objInput.validValue = objInput.value;
    if (!reNumeric.test(strKey)) {
      //alert("Invalid Character Detected!\nKeyCode = " + iKeyCode + "\nCharacter =" + strKey);
      return false;
    }
  }
}
function maskNumericChange(objEvent) {

  var objInput = objEvent.srcElement;

  var isValid = reNumeric.test(objInput.value) || objInput.value.length == 0;

  if ( !isValid ) {
    //alert("Invalid data maskNumericChange");
    objInput.value = objInput.validValue || "";
    objInput.focus();
    objInput.select();
  }
  else {
    objInput.validValue = objInput.value;
  }
}

function maskAlphabetKeyPress(objEvent) {
  var iKeyCode, strKey, objInput;

  iKeyCode = objEvent.keyCode;
  objInput = objEvent.srcElement;

  strKey = String.fromCharCode(iKeyCode);

  isValid = reAlphabet.test(objInput.value) || objInput.value.length == 0;

  if ( isValid ) {
    objInput.validValue = objInput.value;
    if (!reAlphabet.test(strKey)) {
      //alert("Invalid Character Detected!\nKeyCode = " + iKeyCode + "\nCharacter =" + strKey);
      return false;
    }
  }
}
function maskAlphabetChange(objEvent) {

  var objInput = objEvent.srcElement;

  var isValid = reAlphabet.test(objInput.value) || objInput.value.length == 0;

  if ( !isValid ) {
    //alert("Invalid data maskNumericChange");
    objInput.value = objInput.validValue || "";
    objInput.focus();
    objInput.select();
  }
  else {
    objInput.validValue = objInput.value;
  }
}










function trim(str) {
  str = str.replace( /^\s+/g, "" );// strip leading
  return str.replace( /\s+$/g, "" );// strip trailing
}






function alphanumeric (e) {
  if ( !e ) e = window.event;
  var key = e.keyCode == 0 ? e.which : e.keyCode;

  if ( key == 0x20 ) { // <space>
    return true;
  }
  else if ( key == 0x5F ) { // <underscore>
    return true;
  }
  else if ( key > 0x2F && key < 0x3A ) { // 0-9
    return true;
  }
  else if ( key > 0x3F && key < 0x5B ) { // @ A-Z
    return true;
  }
  else if ( key > 0x60 && key < 0x7B ) { // a-z
    return true;
  }
  else {
    return false;
  }
}



function numeric (e) {
  if ( !e ) e = window.event;
  var key = e.keyCode == 0 ? e.which : e.keyCode;

  if ( key < 0x30 || key > 0x39 ) {  // 0-9
    return false;
  }
  else {
    return true;
  }
}



function ascii (e) {
  if ( !e ) e = window.event;
  var key = e.keyCode == 0 ? e.which : e.keyCode;

  alert('You just pressed key = ' + key);
}

