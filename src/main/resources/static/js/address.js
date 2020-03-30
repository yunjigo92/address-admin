$(document).on("keyup", ".phoneNumber", function() {
    $(this).val( $(this).val().replace(/[^0-9]/g, "").replace(/(^02|^0505|^1[0-9]{3}|^0[0-9]{2})([0-9]+)?([0-9]{4})/,"$1-$2-$3").replace("--", "-") );
});



var markingErrorField = function (response) {
    const errorFields = response.responseJSON.errors;

    $('.error-message').remove();
    if(!errorFields){
        alert(response.response.message);
        return;
    }
    var $field, error;

    for(var i=0, length = errorFields.length; i<length;i++){
        error = errorFields[i];
        $field = $('#'+error['field']);

        if($field && $field.length > 0){
            $field.siblings('.error-message').remove();
            $field.after('<span class="error-message text-muted taxt-small text-danger">'+error.defaultMessage+'</span>');
        }
    }
};


var address = {


    init : function () {
        var _this = this;
        $('#btn-save').on('click',function(){
            _this.save();
        });

        $('#btn-update').on('click',function(){
            _this.update();
        });

        $('#btn-delete').on('click',function(){
            _this.delete();
        });

        $('#deleteList').on('click',function () {
            _this.deleteList();
        });

    },
    save : function () {
        var data = {
            id : $('#id').val(),
            addressName : $('#addressName').val(),
            addressEmail : $('#addressEmail').val(),
            addressPhone : $('#addressPhone').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/address',
            dataType: 'json',
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify(data),
            success: function (response) {
                alert('주소록에 추가되었습니다.');
                window.location.href = '/';
            },
            error: function (response) {
                markingErrorField(response);
            }
        });
    },
    update : function () {
        var data = {
            addressName : $('#addressName').val(),
            addressEmail : $('#addressEmail').val(),
            addressPhone : $('#addressPhone').val()
        };

        var addressId = $('#addressId').val();

        $.ajax({
            type : 'PUT',
            url : '/api/v1/address/'+ addressId,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data : JSON.stringify(data),
            success: function (response) {
                alert('주소록이 수정되었습니다.');
                window.location.href = '/';
            },
            error: function (response) {
                markingErrorField(response);
            }
        });
    },
    delete : function () {
        var addressId = $("#addressId").val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/address/'+addressId,
            dataType : 'json',
            contentType : 'application/json; charset=utf-8'
        }).done(function(){
          alert('주소록이 삭제되었습니다.');
          window.location.href='/';
        }).fail(function(error){
            alert(JSON.stringify(error));
        })
    },

    deleteList : function () {
        var ids = new Array();
        $('input:checkbox[name="deleteIds"]').each(function() {
            if(this.checked){//checked 처리된 항목의 값
                ids.push(this.value);
            }
        });

        $.ajax({
            type: 'POST',
            url: '/api/v1/address/delete',
            data : {ids : ids}
        }).done(function(){
            alert('선택한 주소록이 삭제되었습니다.');
            window.location.href='/';
        }).fail(function(error){
            alert(JSON.stringify(error));
        })
    }
}
address.init();



$.ajaxSetup({
    error: function(jqXHR, exception) {
        if (jqXHR.status === 0) {
            alert('Not connect.\n Verify Network.');
        }
        else if (jqXHR.status == 400) {
            alert('Server understood the request, but request content was invalid. [400]');
        }
        else if (jqXHR.status == 401) {
            alert('Unauthorized access. [401]');
        }
        else if (jqXHR.status == 403) {
            alert('접근 권한이 없습니다. [403]');
        }
        else if (jqXHR.status == 404) {
            alert('Requested page not found. [404]');
        }
        else if (jqXHR.status == 500) {
            alert('Internal server error. [500]');
        }
        else if (jqXHR.status == 503) {
            alert('Service unavailable. [503]');
        }
        else if (exception === 'parsererror') {
            alert('Requested JSON parse failed. [Failed]');
        }
        else if (exception === 'timeout') {
            alert('Time out error. [Timeout]');
        }
        else if (exception === 'abort') {
            alert('Ajax request aborted. [Aborted]');
        }
        else {
            alert('Uncaught Error.n' + jqXHR.responseText);
        }
    }
});
