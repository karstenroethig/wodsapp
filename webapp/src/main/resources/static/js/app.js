
$( document ).ready( function() {

	$('.select2-multiple').select2();

	// initialize summernote
	$('.summernote').summernote({
		toolbar: [
			['fontStyle', ['color', 'bold', 'italic', 'underline', 'strikethrough', 'superscript', 'subscript', 'clear']],
			['paragraphStyle', ['style', 'ol', 'ul', 'paragraph']],
			['insert', ['hr', 'table', 'link', 'picture']],
			['misc', ['fullscreen', 'codeview', 'undo', 'redo']]
		]
	});

	// delete modals: transfer the id to the modal form
	$( '#deleteModal' ).on( 'show.bs.modal', function( event ) {
		var button = $( event.relatedTarget ); // Button that triggered the modal
		var id = button.data( 'id' ); // Extract info from data-* attributes

		// Update the modal's content.
		var modal = $( this );
		var link = modal.find( '.btn-danger' );
		var template = link.data( 'href-template' );
		link.attr( 'href', template.replace( '{id}', id ) );
	});
});