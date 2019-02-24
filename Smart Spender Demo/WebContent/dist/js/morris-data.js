/*Morris Init*/
$(function() {
	"use strict";	

	if($('#morris_extra_line_chart').length > 0)
		Morris.Line({
        element: 'morris_extra_line_chart',
        data: [{
            period: '2010',
            iphone: 50,
            ipad: 80,
            itouch: 20
        }, {
            period: '2011',
            iphone: 130,
            ipad: 100,
            itouch: 80
        }, {
            period: '2012',
            iphone: 80,
            ipad: 60,
            itouch: 70
        }, {
            period: '2013',
            iphone: 70,
            ipad: 200,
            itouch: 140
        }, {
            period: '2014',
            iphone: 180,
            ipad: 150,
            itouch: 140
        }, {
            period: '2015',
            iphone: 105,
            ipad: 100,
            itouch: 80
        },
         {
            period: '2016',
            iphone: 250,
            ipad: 150,
            itouch: 200
        }],
        xkey: 'period',
        ykeys: ['iphone', 'ipad', 'itouch'],
        labels: ['iPhone', 'iPad', 'iPod Touch'],
        pointSize: 2,
        fillOpacity: 0,
		lineWidth:2,
		pointStrokeColors:['#0f4fa8', '#b10058', '#f2b701'],
		behaveLikeLine: true,
		gridLineColor: '#878787',
		hideHover: 'auto',
		lineColors: ['#0f4fa8', '#b10058', '#f2b701'],
		resize: true,
		gridTextColor:'#878787',
		gridTextFamily:"Roboto"
        
    });
});