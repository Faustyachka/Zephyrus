$(document).ready(function () {
        //initialize jTable
        $('#PersonTableContainer').jtable({
            title: 'Table of people',
            paging: true, //Enable paging
            pageSize: 10, //Set page size (default: 10)
            sorting: false, //Enable sorting
            defaultSorting: 'Name ASC',
            actions: {
                listAction: '/Zephyrus/customersupport?action=list',

            },
            
            fields : {
				id : {
					key : true,
					list : false
				},
				firstName : {
					title : 'Customer Name'
				},
				lastName : {
					title : 'Customer Second Name'
				},
				email : {
					title : 'Email'
				},

				status : {
					title : 'Choose',
					display : function(data) {
						return '<input type="radio" name="radiobutton" class="radiobutton" value="'+data.record.id+'"/>';
					},
					create : false,
					edit : false

				}

			}
		});
        $('#PersonTableContainer').jtable('load');
    });