$(document).ready(function (){
	$('#dataGrid a[_op="edit"]').bind('click', function(){
        var _tr = $(this).closest('tr');
        new ctzcPresetEditor({
            width: 380,
            height: 320,
            data: {
                issueNumber: $('td', _tr).eq(0).text(),
                playName: $('td', _tr).eq(1).text(),
                playId: $(this).attr("playId"),
            },
            callback: function(){
                $('#qForm').submit();
            }
        });
        return false;
    });
});