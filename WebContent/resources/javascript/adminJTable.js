$(document).ready(function () {
        //initialize jTable
        $('#PersonTableContainer').jtable({
            title: 'Table of people',
            paging: true,
            pageSize: 10, 
            sorting: true, 
            actions: {
                listAction: '/Zephyrus/admin?action=list',

            },
            recordsLoaded: function(event, data) {
                $('.serialcheck').click(function() {
                	var id = $(this).val();
                	var status;
                	if ($(this).is(':checked')) {
                		status=1;
                	} else {
                		status=0;
                	}
                	$.post('blocking',{id:id, status:status},function(rsp){
                		if (rsp!="success")
                			alert(rsp);
                    });                       	                                           
                });
            },
            fields: {
                id: {
                    key: true,
                    list: false
                },
                firstName: {
                    title: 'First Name'
                },
                lastName: {
                    title: 'Last Name'
                },
                email: {
                    title: 'Email'
                },
                
                status: {
                    title: 'Blocked',                   
                    display: function (data) {
                    	if (data.record.status==1) {
                       return '<input type="checkbox" class = "serialcheck" checked="checked" name="blockstatus" value="'+data.record.id+'"/>';
                    	} else {
                    		return '<input type="checkbox" class = "serialcheck" name="blockstatus"  value="'+data.record.id+'"/>';
                    	}
                    }
                },
                roleId: {
                	title : 'Role',
					display : function(data) {
						return data.record.role.roleName;
					}
                    
                }
                
            }
        });  
        $('#PersonTableContainer').jtable('load');
    });