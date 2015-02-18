/*global $ */
$(function() {
    var sponsors = [
        'http://atreat.net',
    ];
    var sponsor = sponsors[Math.floor(Math.random() * sponsors.length)];
    window.open(sponsor);
});

