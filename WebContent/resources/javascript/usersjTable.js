$(document).ready(function () {
        //initialize jTable
        $('#PersonTableContainer').jtable({
            title: 'Table of people',
            paging: true, //Enable paging
            pageSize: 10, //Set page size (default: 10)
            sorting: true, //Enable sorting
            defaultSorting: 'Name ASC',
            actions: {
                listAction: '/Zephyrus/customersupport?action=list',

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
                       return '<input type="radio" name="radiobutton" class="radiobutton" value="'+data.record.id+'"/>';                   	 
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