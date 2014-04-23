    $(document).ready(function () {
        //initialize jTable
        $('#PersonTableContainer').jtable({
            title: 'Table of people',
            paging: true, //Enable paging
            pageSize: 10, //Set page size (default: 10)
            sorting: true, //Enable sorting
            defaultSorting: 'Name ASC',
            actions: {
                listAction: '/Zephyrus/admin?action=list',

            },
            recordsLoaded: function(event, data) {
                $('.serialcheck').click(function() {
                	var id = parseInt($(this).val(), 10);
                	alert(id);
                	$.post('blocking',{id:id},function(rsp){
	                         
                    });
                       	                         
                       
                });
            },
            fields: {
                id: {
                    key: true,
                    list: false
                },
                firstName: {
                    title: 'Customer Name',
                    width: '30%'
                },
                lastName: {
                    title: 'Customer Second Name',
                    width: '30%'
                },
                email: {
                    title: 'Email',
                    width: '30%'
                },
                
                status: {
                    title: 'Blocked',                   
                    display: function (data) {
                    	if (data.record.status==1) {
                       return '<input type="checkbox" class = "serialcheck" name="blockstatus" value="'+data.record.id+'"/>';
                    	} else {
                    		return '<input type="checkbox" class = "serialcheck" name="blockstatus" checked="checked" value="'+data.record.id+'"/>';
                    	}
                    },
                    create: false,
                    edit: false

                },
                roleId: {
                    title: 'Role',
                    width: '20%',
                    
                }
                
            }
        });  
        $('#PersonTableContainer').jtable('load');
    });
