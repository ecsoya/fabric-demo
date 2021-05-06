const baseURL = $('#baseURL').val() + '/ipfs';


	let bookmarkMap = new Map();

	let dataTable = $('#dataTable')
			.DataTable(
					{
						"ajax" : function(data, callback) {
							let pageSize = data.length;
							let currentPage = data.start / data.length;
							let bookmark = bookmarkMap[currentPage];
							if (bookmark == undefined) {
								bookmark = '';
							}
							$.ajax({
								url : baseURL + "/list",
								data : {
									bookmark : bookmark,
									pageSize : pageSize,
									currentPage : currentPage,
									type : $('#type').val(),
									value: $('#value').val()
								}
							}).then(function success(res) {
								bookmarkMap[currentPage + 1] = res.bookmark;
								if (res.data) {
									for (var i = 0; i < res.data.length; i++) {
										res.data[i].index = i + 1;
									}
								}
								callback(res);
							}, function fail(data, status) {

							});
						},
						"paging" : true,
						"ordering" : false,
						"info" : false,
						"searching" : false,
						"processing" : true,
						"serverSide" : true,
						"scrollY" : '50vh',
						"scrollCollapse" : true,
						"scroller" : {
							"loadingIndicator" : true
						},
						"language" : {
							"lengthMenu" : "显示 _MENU_ 条记录",
							"emptyTable" : "空",
							"processing" : "加载中……",
							"paginate" : {
								"first" : "第一页",
								"last" : "最后一页",
								"next" : "下一页",
								"previous" : "上一页"
							},
						},
						"columns" : [
								{
									"name" : "#",
									"data" : function(row) {
										return row.index;
									}
								},
								{
									"name" : "Name",
									"data" : function(row) {
										return '<a href="/explorer/history?type=ipfs&key='+row.id+'" target="_blank">' + row.name + '</a>' ;
									}
								},
								{
									"name" : "ContentType",
									"data" : function(row) {
										return row.contentType;
									}
								},
								{
									"name" : "Hash",
									"data" : function(row) {
										return '<a href="'+ $('#gateway').val() +  row.hash+'">' + row.hash + '</a>' ;
									}
								},
								{
									"name" : "CreateTime",
									"data" : function(row) {
										return format(row.createTime);
									}
								},
								{
									"name" : "operations",
									"data" : function(row) {
										var html = '';
										html += '<div class="btn-group" role="group" aria-label="操作">';
										html += '  <button type="button" class="btn btn-danger btn-sm" onclick="toEdit(\''
												+ row.id + '\')">更新</button>';
										html += '</div>';
										return html;
									}
								} ]
					});

	$('#refresh').click(function() {
		refreshDataTable();
	});
	$('#search').click(function() {
		refreshDataTable(true);
	});

	function refreshDataTable(force) {
		if (force != undefined && force === true) {
			bookmarkMap = new Map();
		}
		if (dataTable != undefined && dataTable != null) {
			dataTable.ajax.reload();
		}
	}


function toEdit(id) {
	$('#editId').val(id);
	$("#uploadFile").click(); 
}

function upload() {
	$('#editId').val('');
	$("#uploadFile").click(); 
}

function doUploadFile(obj) {
	toastInfo('文件上传中……');	
	var formData = new FormData();
    var files = obj.files[0];
    formData.append("id", $('#editId').val());
    formData.append("address", $("#address").val());
    formData.append("gateway", $("#gateway").val());
    //formData.append("name", $("#name").val());
    //formData.append("owner", $("#owner").val());
    formData.append("owner", 'AngryRED');
    formData.append("file", files);
    $.ajax({
        url: baseURL + "/add",
        type: 'POST',
        data: formData,
        processData: false,
        contentType: false, 
        dataType: 'json',
        success:function (res) {
        	if(res.status == 1) {
        		toastSuccess("上传成功")
				refreshDataTable(true);
			} else {
				toastError('上传失败: ' + res.errorMsg);
			}
			$('#uploadForm')[0].reset();
        },error:function (res) {
        	toastError('上传失败');
        }
    });
}

function toDelete(id, type) {
	toastInfo('拼命删除中……');
	$.ajax({
		url : baseURL + "/remove",
		data : {
			id : id,
			type : type
		}
	}).then(function success(res) {
		toastSuccess('删除成功');

		setTimeout(function() {
			// refreshDataTable(true);
			window.location = baseURL;
		}, 2000);
	}, function fail(data, status) {
		toastError('删除失败');
	});
}


function toastSuccess(msg) {
	$.toast().reset('all');
	$.toast({
		text : msg,
		showHideTransition : 'slide',
		icon : 'success',
		loaderBg : '#f96868',
		position : 'top-center',
		hideAfter : 2000,
		showClose : false
	});
}

function toastError(msg) {
	$.toast().reset('all');
	$.toast({
		text : msg,
		stack : 1,
		showHideTransition : 'slide',
		icon : 'error',
		loaderBg : '#f2a654',
		position : 'top-center',
		hideAfter : 2000,
		showClose : false
	})
}

function toastInfo(msg) {
	$.toast().reset('all');
	$.toast({
		text : msg,
		stack : 1,
		showHideTransition : 'slide',
		icon : 'info',
		loaderBg : '#46c35f',
		position : 'top-center',
		hideAfter : false,
		showClose : false
	})
}

function strftime(fmt, str) {
	var date = new Date(str);
    if (fmt == undefined){
        fmt = 'yyyy-MM-dd hh:mm:ss.S';
    }
    var o = {
        "M+" : date.getMonth()+1,                 // 月份
        "d+" : date.getDate(),                    // 日
        "h+" : date.getHours(),                   // 小时
        "m+" : date.getMinutes(),                 // 分
        "s+" : date.getSeconds(),                 // 秒
        "q+" : Math.floor((date.getMonth()+3)/3), // 季度
        "S"  : date.getMilliseconds()             // 毫秒
    };
    if(/(y+)/.test(fmt))
        fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)
        if(new RegExp("("+ k +")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
    return fmt;
};

function format(date) {
	return strftime('yyyy-MM-dd hh:mm:ss.S', date);
}