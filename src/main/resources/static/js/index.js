var index = {
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

    },
    save : function () {
        var data = {
            title : $('#title').val(),
            author : $('#author').val(),
            content : $('#content').val()

        };

        $.ajax({
            type : 'POST',
            url : '/api/v1/posts',
            dataType : 'json',
            contentType : 'application/json;charset=utf-8',
            data : JSON.stringify(data)
        }).done(function (){
            alert('글이 등록되었습니다.');
            window.location.href ='/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    },
    update : function () {
        var data = {
            title: $('#title').val(),
            content : $('#content').val()
        };

        var id = $('#id').val();

        $.ajax({
            type : 'PUT',
            url : '/api/v1/posts/'+ id,
            dataType: 'JSON',
            contentType: 'application/json; charset=utf-8',
            data : JSON.stringify(data)

        }).done(function () {
            alert('글이 수정되었습니다.');
            window.location.href = '/';
        }).fail(function (error){

        });
    },
    delete : function () {
        var id = $("#id").val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/'+id,
            dataType : 'json',
            contentType : 'application/json; charset=utf-8'
        }).done(function(){
          alert('글이 삭제되었습니다.');
          window.location.href='/';
        }).fail(function(error){
            alert(JSON.stringify(error));
        })
    }
}
index.init();


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
