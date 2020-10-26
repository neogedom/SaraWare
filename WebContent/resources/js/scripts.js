var $element1 = $("#frmCronogramaNovo\\:schedule");

function verificar(xhr, status, args, dlg, tbl) {
	if (args.validationFailed) {
		PF(dlg).jq.effect("shake", {
			times : 5
		}, 100);
	} else {
		PF(dlg).hide();
		PF(tbl).clearFilters();
	}
}



function teste()
{
	
	
	jQuery('#frmCronogramaNovo:\\teste').fullCalendar({
	   
	    views: {
	        agendaFourDay: {
	            type: 'agendaFourDay',
	            duration: { days: 4 },
	            buttonText: '4 day'
	        }
	    }
	});
	
}


