// In this example, we must bind a 'change' event handler to
    // our checkboxes, then interact with the mixer via
    // its .filter() API methods.

    //replacing "container" with "grid-container"

    var containerEl = document.querySelector('.grid-container');
    var checkboxGroup = document.querySelector('.checkbox-group');
    var checkboxes = checkboxGroup.querySelectorAll('input[type="checkbox"]');

    var mixer = mixitup(containerEl);

    checkboxGroup.addEventListener('change', function() {
        var selectors = [];

        // Iterate through all checkboxes, pushing the
        // values of those that are checked into an array

        for (var i = 0; i < checkboxes.length; i++) {
            var checkbox = checkboxes[i];

            if (checkbox.checked) selectors.push(checkbox.value);
        }

        // If there are values in the array, join it into a string
        // using your desired logic, and send to the mixer's .filter()
        // method, otherwise filter by 'all'

        var selectorString = selectors.length > 0 ?
            selectors.join(',') : // or '.' for AND logic
            'all';

        mixer.filter(selectorString);
    });
