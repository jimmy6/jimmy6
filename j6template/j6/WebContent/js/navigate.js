function goPage(url)
{
	document.location.href=url;
}

function toggleSection(id)
{ 
	var section = document.getElementById(id); 
    if(section.style.display == "none")
    {
        section.style.display = "block";
    }
}

function hideSection(id)
{
	var section = document.getElementById(id); 
    if(section.style.display == "block")
    {
        section.style.display = "none";
    }
}