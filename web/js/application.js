
$(function() {
	$('#search-form').on('submit', function() {
		var urls = {
			music: 'SearchMusicServlet',
			artist: 'SearchArtistServlet',
			tag: 'SearchTagServlet',
		};
		var type = $('.search-form-type:checked').val();
		$(this).attr('action', urls[type]);
		if (type === 'music' || type === 'artist') {
			$('#search-form-text').attr('name', 'q');
		} else if (type === 'tag') {
			$('#search-form-text').attr('name', 't');
		} else {
			return false;
		}
	});
});