/* 
 * This is the client-side logic for manipulating the DOM.
 * 
 * This code is heavily dependent on JQuery 1.13.x and 
 * bootstrap plus custom css.
 * 
 * Author: Jim Lombardo
 * Version: 1.00
 */

// Auto-start anonymous functions with JQuery
// Note that all variables and functions are private, except where noted
(function ($, window, document) {
    $(function () {
        // ==================================================================
        // Private properties
        // ==================================================================

        // normal properties
        var curDate = new Date();
        //var authorsBaseUrl = "AuthorController";
        var authorsBaseUrl = "https://localhost:8181/bookWebApp/api/v1/authors";

        // properties that cache JQuery selectors
        var $document = $(document);
        var $body = $('body');
        var $authorTableBody = $('#authorTblBody');


        // ==================================================================
        // Private event handlers and functions
        // ==================================================================

        /*
         * This is a JQuery-specific event that only fires after all HTML 
         * is loaded, except images, and the DOM is ready. You must be careful 
         * to only act on DOM elements from JavaScript AFTER they have been 
         * loaded.
         * 
         * Gets a collection of author objects as a JSON array from the server.
         */
        $document.ready(function () {
            console.log("document ready event fired!");

            // Make sure we only do this on pages with an author list
            if ($body.attr('class') === 'authorList') {
                $.ajax({
                    type: 'GET',
                    url: authorsBaseUrl,
                    success: function (embedded) {
                        displayAuthors(embedded._embedded.authors);
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        alert("Could not get authors for this user due to: " + errorThrown.toString());
                    }
                });
            }
        });
        /*
         * Loops over the authors collection returned by the server and 
         * extracts individual author objects and their properties, then 
         * builds table rows and columns using this data. Each row is 
         * dynamically appended to the table body DOM element.
         */
        function displayAuthors(authors) {
            $.each(authors, function (index, author) {
                var row = '<tr class="authorListRow">' +
                        '<td></td>' +
                        '<td>' + author.authorName + '</td>' +
                        '<td>' + author.dateAdded + '</td>' +
                        '</tr>';
                $authorTableBody.append(row);
            });
        }

//        function displayAuthors(authors) {
//            $.each(authors, function (index, author) {
//                var row = '<tr class="authorListRow">' +
//                        '<td>' + author.authorId + '</td>' +
//                        '<td>' + author.authorName + '</td>' +
//                        '<td>' + author.dateAdded + '</td>' +
//                        '</tr>';
//                $authorTableBody.append(row);
//            });
//        }

        function displayHotels(embedded) {
            $.each(embedded.hotels, function (index, hotel) {
                var row = '<tr class="hotelListRow">' +
                        '<td>' + hotel.name + '</td>' +
                        '<td>' + hotel.city + '</td>' +
                        '</tr>';
                $hotelTableBody.append(row);
            });
        }


        $authorTableBody.on('click', 'tr', function () {
            console.log('row click event fired');
            var authorId = $(this).find("td").contents()[0].data;
            console.log(authorId);
            $.ajax({
                type: 'POST',
                contentType: 'application/json',
                url: authorsBaseUrl + "?action=findByIdAjax",
                dataType: "json",
                data: JSON.stringify({"authorId": authorId}),
                success: function (author) {
                    $('#addEditAuthor').show();
                    $('#authorId').val(author.authorId);
                    $('#authorName').val(author.authorName);
                    $('#dateAdded').val(author.dateAdded);
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert("Could not get author by id due to: " + errorThrown.toString());
                }
            });
        });

        $authorTableBody.on('mouseover', 'tr', function () {
            console.log('row mouseover event fired');
            $(this).toggleClass('hover');
        });

        $authorTableBody.on('mouseout', 'tr', function () {
            console.log('row mouseout event fired');
            $(this).toggleClass('hover');
        });

    });
}(window.jQuery, window, document));

