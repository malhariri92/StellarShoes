var measureMedia = window.matchMedia( "(min-width: 850px)" );

// Set width of slideNav
function openNav() {
    document.getElementById("mySlideNav").style.width = "100%";
}

// Set width back
function closeNav() {
    if(measureMedia.matches) {
        document.getElementById("mySlideNav").style.width = "100%";
    }
    else {
        document.getElementById("mySlideNav").style.width = "0";
    }
    
}