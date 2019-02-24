/*Dashboard Init*/
 
"use strict"; 

/*****Load function start*****/
$(window).load(function(){
	window.setTimeout(function(){
		$.toast({
			heading: 'Welcome to Smart Spender',
			text: 'Record your Income & Expenses. You can also set your monthly budgets and track it. Review your transactions and keep your fund up-to-date.',
			position: 'top-right',
			loaderBg:'#f2b701',
			icon: 'success',
			hideAfter: 3500, 
			stack: 6
		});
	}, 3000);
});
/*****Load function* end*****/