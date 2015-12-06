/*!
 * jQuery Cookie Plugin v1.4.0
 * https://github.com/carhartl/jquery-cookie
 *
 * Copyright 2013 Klaus Hartl
 * Released under the MIT license
 */
(function (factory) {
	if (typeof define === 'function' && define.amd) {
		// AMD. Register as anonymous module.
		define(['jquery'], factory);
	} else {
		// Browser globals.
		factory(jQuery);
	}
}(function ($) {

	var pluses = /\+/g;

	function encode(s) {
		return config.raw ? s : encodeURIComponent(s);
	}

	function decode(s) {
		return config.raw ? s : decodeURIComponent(s);
	}

	function stringifyCookieValue(value) {
		return encode(config.json ? JSON.stringify(value) : String(value));
	}

	function parseCookieValue(s) {
		if (s.indexOf('"') === 0) {
			// This is a quoted cookie as according to RFC2068, unescape...
			s = s.slice(1, -1).replace(/\\"/g, '"').replace(/\\\\/g, '\\');
		}

		try {
			// Replace server-side written pluses with spaces.
			// If we can't decode the cookie, ignore it, it's unusable.
			s = decodeURIComponent(s.replace(pluses, ' '));
		} catch(e) {
			return;
		}

		try {
			// If we can't parse the cookie, ignore it, it's unusable.
			return config.json ? JSON.parse(s) : s;
		} catch(e) {}
	}

	function read(s, converter) {
		var value = config.raw ? s : parseCookieValue(s);
		return $.isFunction(converter) ? converter(value) : value;
	}

	var config = $.cookie = function (key, value, options) {

		// Write
		if (value !== undefined && !$.isFunction(value)) {
			options = $.extend({}, config.defaults, options);

			if (typeof options.expires === 'number') {
				var days = options.expires, t = options.expires = new Date();
				t.setDate(t.getDate() + days);
			}

			return (document.cookie = [
				encode(key), '=', stringifyCookieValue(value),
				options.expires ? '; expires=' + options.expires.toUTCString() : '', // use expires attribute, max-age is not supported by IE
				options.path    ? '; path=' + options.path : '',
				options.domain  ? '; domain=' + options.domain : '',
				options.secure  ? '; secure' : ''
			].join(''));
		}

		// Read

		var result = key ? undefined : {};

		// To prevent the for loop in the first place assign an empty array
		// in case there are no cookies at all. Also prevents odd result when
		// calling $.cookie().
		var cookies = document.cookie ? document.cookie.split('; ') : [];

		for (var i = 0, l = cookies.length; i < l; i++) {
			var parts = cookies[i].split('=');
			var name = decode(parts.shift());
			var cookie = parts.join('=');

			if (key && key === name) {
				// If second argument (value) is a function it's a converter...
				result = read(cookie, value);
				break;
			}

			// Prevent storing a cookie that we couldn't decode.
			if (!key && (cookie = read(cookie)) !== undefined) {
				result[name] = cookie;
			}
		}

		return result;
	};

	config.defaults = {};

	$.removeCookie = function (key, options) {
		if ($.cookie(key) !== undefined) {
			// Must not alter options, thus extending a fresh object...
			$.cookie(key, '', $.extend({}, options, { expires: -1 }));
			return true;
		}
		return false;
	};

}));

/*!
 * jQuery blockUI plugin
 * Version 2.54 (17-DEC-2012)
 * @requires jQuery v1.3 or later
 *
 * Examples at: http://malsup.com/jquery/block/
 * Copyright (c) 2007-2012 M. Alsup
 * Dual licensed under the MIT and GPL licenses:
 * http://www.opensource.org/licenses/mit-license.php
 * http://www.gnu.org/licenses/gpl.html
 *
 * Thanks to Amir-Hossein Sobhi for some excellent contributions!
 */

;(function() {
"use strict";

	function setup($) {
		if (/^1\.(0|1|2)/.test($.fn.jquery)) {
			/*global alert:true */
			alert('blockUI requires jQuery v1.3 or later!  You are using v' + $.fn.jquery);
			return;
		}

		$.fn._fadeIn = $.fn.fadeIn;

		var noOp = $.noop || function() {};

		// this bit is to ensure we don't call setExpression when we shouldn't (with extra muscle to handle
		// retarded userAgent strings on Vista)
		var msie = /MSIE/.test(navigator.userAgent);
		var ie6  = /MSIE 6.0/.test(navigator.userAgent);
		var mode = document.documentMode || 0;
		// var setExpr = msie && (($.browser.version < 8 && !mode) || mode < 8);
		var setExpr = $.isFunction( document.createElement('div').style.setExpression );

		// global $ methods for blocking/unblocking the entire page
		$.blockUI   = function(opts) { install(window, opts); };
		$.unblockUI = function(opts) { remove(window, opts); };

		// convenience method for quick growl-like notifications  (http://www.google.com/search?q=growl)
		$.growlUI = function(title, message, timeout, onClose) {
			var $m = $('<div class="growlUI"></div>');
			if (title) $m.append('<h1>'+title+'</h1>');
			if (message) $m.append('<h2>'+message+'</h2>');
			if (timeout === undefined) timeout = 3000;
			$.blockUI({
				message: $m, fadeIn: 700, fadeOut: 1000, centerY: false,
				timeout: timeout, showOverlay: false,
				onUnblock: onClose,
				css: $.blockUI.defaults.growlCSS
			});
		};

		// plugin method for blocking element content
		$.fn.block = function(opts) {
			var fullOpts = $.extend({}, $.blockUI.defaults, opts || {});
			this.each(function() {
				var $el = $(this);
				if (fullOpts.ignoreIfBlocked && $el.data('blockUI.isBlocked'))
					return;
				$el.unblock({ fadeOut: 0 });
			});

			return this.each(function() {
				if ($.css(this,'position') == 'static')
					this.style.position = 'relative';
				this.style.zoom = 1; // force 'hasLayout' in ie
				install(this, opts);
			});
		};

		// plugin method for unblocking element content
		$.fn.unblock = function(opts) {
			return this.each(function() {
				remove(this, opts);
			});
		};

		$.blockUI.version = 2.54; // 2nd generation blocking at no extra cost!

		// override these in your code to change the default behavior and style
		$.blockUI.defaults = {
			// message displayed when blocking (use null for no message)
			message:  '<h1>Please wait...</h1>',

			title: null,		// title string; only used when theme == true
			draggable: true,	// only used when theme == true (requires jquery-ui.js to be loaded)

			theme: false, // set to true to use with jQuery UI themes

			// styles for the message when blocking; if you wish to disable
			// these and use an external stylesheet then do this in your code:
			// $.blockUI.defaults.css = {};
			css: {
				padding:	0,
				margin:		0,
				width:		'30%',
				top:		'40%',
				left:		'35%',
				textAlign:	'center',
				color:		'#000',
				border:		'3px solid #aaa',
				backgroundColor:'#fff',
				cursor:		'wait'
			},

			// minimal style set used when themes are used
			themedCSS: {
				width:	'30%',
				top:	'40%',
				left:	'35%'
			},

			// styles for the overlay
			overlayCSS:  {
				backgroundColor:	'#000',
				opacity:			0.6,
				cursor:				'wait'
			},

			// style to replace wait cursor before unblocking to correct issue
			// of lingering wait cursor
			cursorReset: 'default',

			// styles applied when using $.growlUI
			growlCSS: {
				width:		'350px',
				top:		'10px',
				left:		'',
				right:		'10px',
				border:		'none',
				padding:	'5px',
				opacity:	0.6,
				cursor:		'default',
				color:		'#fff',
				backgroundColor: '#000',
				'-webkit-border-radius':'10px',
				'-moz-border-radius':	'10px',
				'border-radius':		'10px'
			},

			// IE issues: 'about:blank' fails on HTTPS and javascript:false is s-l-o-w
			// (hat tip to Jorge H. N. de Vasconcelos)
			/*jshint scripturl:true */
			iframeSrc: /^https/i.test(window.location.href || '') ? 'javascript:false' : 'about:blank',

			// force usage of iframe in non-IE browsers (handy for blocking applets)
			forceIframe: false,

			// z-index for the blocking overlay
			baseZ: 1000,

			// set these to true to have the message automatically centered
			centerX: true, // <-- only effects element blocking (page block controlled via css above)
			centerY: true,

			// allow body element to be stetched in ie6; this makes blocking look better
			// on "short" pages.  disable if you wish to prevent changes to the body height
			allowBodyStretch: true,

			// enable if you want key and mouse events to be disabled for content that is blocked
			bindEvents: true,

			// be default blockUI will supress tab navigation from leaving blocking content
			// (if bindEvents is true)
			constrainTabKey: true,

			// fadeIn time in millis; set to 0 to disable fadeIn on block
			fadeIn:  200,

			// fadeOut time in millis; set to 0 to disable fadeOut on unblock
			fadeOut:  400,

			// time in millis to wait before auto-unblocking; set to 0 to disable auto-unblock
			timeout: 0,

			// disable if you don't want to show the overlay
			showOverlay: true,

			// if true, focus will be placed in the first available input field when
			// page blocking
			focusInput: true,

			// suppresses the use of overlay styles on FF/Linux (due to performance issues with opacity)
			// no longer needed in 2012
			// applyPlatformOpacityRules: true,

			// callback method invoked when fadeIn has completed and blocking message is visible
			onBlock: null,

			// callback method invoked when unblocking has completed; the callback is
			// passed the element that has been unblocked (which is the window object for page
			// blocks) and the options that were passed to the unblock call:
			//	onUnblock(element, options)
			onUnblock: null,

			// callback method invoked when the overlay area is clicked.
			// setting this will turn the cursor to a pointer, otherwise cursor defined in overlayCss will be used.
			onOverlayClick: null,

			// don't ask; if you really must know: http://groups.google.com/group/jquery-en/browse_thread/thread/36640a8730503595/2f6a79a77a78e493#2f6a79a77a78e493
			quirksmodeOffsetHack: 4,

			// class name of the message block
			blockMsgClass: 'blockMsg',

			// if it is already blocked, then ignore it (don't unblock and reblock)
			ignoreIfBlocked: false
		};

		// private data and functions follow...

		var pageBlock = null;
		var pageBlockEls = [];

		function install(el, opts) {
			var css, themedCSS;
			var full = (el == window);
			var msg = (opts && opts.message !== undefined ? opts.message : undefined);
			opts = $.extend({}, $.blockUI.defaults, opts || {});

			if (opts.ignoreIfBlocked && $(el).data('blockUI.isBlocked'))
				return;

			opts.overlayCSS = $.extend({}, $.blockUI.defaults.overlayCSS, opts.overlayCSS || {});
			css = $.extend({}, $.blockUI.defaults.css, opts.css || {});
			if (opts.onOverlayClick)
				opts.overlayCSS.cursor = 'pointer';

			themedCSS = $.extend({}, $.blockUI.defaults.themedCSS, opts.themedCSS || {});
			msg = msg === undefined ? opts.message : msg;

			// remove the current block (if there is one)
			if (full && pageBlock)
				remove(window, {fadeOut:0});

			// if an existing element is being used as the blocking content then we capture
			// its current place in the DOM (and current display style) so we can restore
			// it when we unblock
			if (msg && typeof msg != 'string' && (msg.parentNode || msg.jquery)) {
				var node = msg.jquery ? msg[0] : msg;
				var data = {};
				$(el).data('blockUI.history', data);
				data.el = node;
				data.parent = node.parentNode;
				data.display = node.style.display;
				data.position = node.style.position;
				if (data.parent)
					data.parent.removeChild(node);
			}

			$(el).data('blockUI.onUnblock', opts.onUnblock);
			var z = opts.baseZ;

			// blockUI uses 3 layers for blocking, for simplicity they are all used on every platform;
			// layer1 is the iframe layer which is used to supress bleed through of underlying content
			// layer2 is the overlay layer which has opacity and a wait cursor (by default)
			// layer3 is the message content that is displayed while blocking
			var lyr1, lyr2, lyr3, s;
			if (msie || opts.forceIframe)
				lyr1 = $('<iframe class="blockUI" style="z-index:'+ (z++) +';display:none;border:none;margin:0;padding:0;position:absolute;width:100%;height:100%;top:0;left:0" src="'+opts.iframeSrc+'"></iframe>');
			else
				lyr1 = $('<div class="blockUI" style="display:none"></div>');

			if (opts.theme)
				lyr2 = $('<div class="blockUI blockOverlay ui-widget-overlay" style="z-index:'+ (z++) +';display:none"></div>');
			else
				lyr2 = $('<div class="blockUI blockOverlay" style="z-index:'+ (z++) +';display:none;border:none;margin:0;padding:0;width:100%;height:100%;top:0;left:0"></div>');

			if (opts.theme && full) {
				s = '<div class="blockUI ' + opts.blockMsgClass + ' blockPage ui-dialog ui-widget ui-corner-all" style="z-index:'+(z+10)+';display:none;position:fixed">';
				if ( opts.title ) {
					s += '<div class="ui-widget-header ui-dialog-titlebar ui-corner-all blockTitle">'+(opts.title || '&nbsp;')+'</div>';
				}
				s += '<div class="ui-widget-content ui-dialog-content"></div>';
				s += '</div>';
			}
			else if (opts.theme) {
				s = '<div class="blockUI ' + opts.blockMsgClass + ' blockElement ui-dialog ui-widget ui-corner-all" style="z-index:'+(z+10)+';display:none;position:absolute">';
				if ( opts.title ) {
					s += '<div class="ui-widget-header ui-dialog-titlebar ui-corner-all blockTitle">'+(opts.title || '&nbsp;')+'</div>';
				}  
				s += '<div class="ui-widget-content ui-dialog-content"></div>';
				s += '</div>';
			}
			else if (full) {
				s = '<div class="blockUI ' + opts.blockMsgClass + ' blockPage" style="z-index:'+(z+10)+';display:none;position:fixed"></div>';
			}
			else {
				s = '<div class="blockUI ' + opts.blockMsgClass + ' blockElement" style="z-index:'+(z+10)+';display:none;position:absolute"></div>';
			}
			lyr3 = $(s);

			// if we have a message, style it
			if (msg) {
				if (opts.theme) {
					lyr3.css(themedCSS);
					lyr3.addClass('ui-widget-content');
				}
				else
					lyr3.css(css);
			}

			// style the overlay
			if (!opts.theme /*&& (!opts.applyPlatformOpacityRules)*/)
				lyr2.css(opts.overlayCSS);
			lyr2.css('position', full ? 'fixed' : 'absolute');

			// make iframe layer transparent in IE
			if (msie || opts.forceIframe)
				lyr1.css('opacity',0.0);

			//$([lyr1[0],lyr2[0],lyr3[0]]).appendTo(full ? 'body' : el);
			var layers = [lyr1,lyr2,lyr3], $par = full ? $('body') : $(el);
			$.each(layers, function() {
				this.appendTo($par);
			});

			if (opts.theme && opts.draggable && $.fn.draggable) {
				lyr3.draggable({
					handle: '.ui-dialog-titlebar',
					cancel: 'li'
				});
			}

			// ie7 must use absolute positioning in quirks mode and to account for activex issues (when scrolling)
			var expr = setExpr && (!$.support.boxModel || $('object,embed', full ? null : el).length > 0);
			if (ie6 || expr) {
				// give body 100% height
				if (full && opts.allowBodyStretch && $.support.boxModel)
					$('html,body').css('height','100%');

				// fix ie6 issue when blocked element has a border width
				if ((ie6 || !$.support.boxModel) && !full) {
					var t = sz(el,'borderTopWidth'), l = sz(el,'borderLeftWidth');
					var fixT = t ? '(0 - '+t+')' : 0;
					var fixL = l ? '(0 - '+l+')' : 0;
				}

				// simulate fixed position
				$.each(layers, function(i,o) {
					var s = o[0].style;
					s.position = 'absolute';
					if (i < 2) {
						if (full)
							s.setExpression('height','Math.max(document.body.scrollHeight, document.body.offsetHeight) - (jQuery.support.boxModel?0:'+opts.quirksmodeOffsetHack+') + "px"');
						else
							s.setExpression('height','this.parentNode.offsetHeight + "px"');
						if (full)
							s.setExpression('width','jQuery.support.boxModel && document.documentElement.clientWidth || document.body.clientWidth + "px"');
						else
							s.setExpression('width','this.parentNode.offsetWidth + "px"');
						if (fixL) s.setExpression('left', fixL);
						if (fixT) s.setExpression('top', fixT);
					}
					else if (opts.centerY) {
						if (full) s.setExpression('top','(document.documentElement.clientHeight || document.body.clientHeight) / 2 - (this.offsetHeight / 2) + (blah = document.documentElement.scrollTop ? document.documentElement.scrollTop : document.body.scrollTop) + "px"');
						s.marginTop = 0;
					}
					else if (!opts.centerY && full) {
						var top = (opts.css && opts.css.top) ? parseInt(opts.css.top, 10) : 0;
						var expression = '((document.documentElement.scrollTop ? document.documentElement.scrollTop : document.body.scrollTop) + '+top+') + "px"';
						s.setExpression('top',expression);
					}
				});
			}

			// show the message
			if (msg) {
				if (opts.theme)
					lyr3.find('.ui-widget-content').append(msg);
				else
					lyr3.append(msg);
				if (msg.jquery || msg.nodeType)
					$(msg).show();
			}

			if ((msie || opts.forceIframe) && opts.showOverlay)
				lyr1.show(); // opacity is zero
			if (opts.fadeIn) {
				var cb = opts.onBlock ? opts.onBlock : noOp;
				var cb1 = (opts.showOverlay && !msg) ? cb : noOp;
				var cb2 = msg ? cb : noOp;
				if (opts.showOverlay)
					lyr2._fadeIn(opts.fadeIn, cb1);
				if (msg)
					lyr3._fadeIn(opts.fadeIn, cb2);
			}
			else {
				if (opts.showOverlay)
					lyr2.show();
				if (msg)
					lyr3.show();
				if (opts.onBlock)
					opts.onBlock();
			}

			// bind key and mouse events
			bind(1, el, opts);

			if (full) {
				pageBlock = lyr3[0];
				pageBlockEls = $(':input:enabled:visible',pageBlock);
				if (opts.focusInput)
					setTimeout(focus, 20);
			}
			else
				center(lyr3[0], opts.centerX, opts.centerY);

			if (opts.timeout) {
				// auto-unblock
				var to = setTimeout(function() {
					if (full)
						$.unblockUI(opts);
					else
						$(el).unblock(opts);
				}, opts.timeout);
				$(el).data('blockUI.timeout', to);
			}
		}

		// remove the block
		function remove(el, opts) {
			var full = (el == window);
			var $el = $(el);
			var data = $el.data('blockUI.history');
			var to = $el.data('blockUI.timeout');
			if (to) {
				clearTimeout(to);
				$el.removeData('blockUI.timeout');
			}
			opts = $.extend({}, $.blockUI.defaults, opts || {});
			bind(0, el, opts); // unbind events

			if (opts.onUnblock === null) {
				opts.onUnblock = $el.data('blockUI.onUnblock');
				$el.removeData('blockUI.onUnblock');
			}

			var els;
			if (full) // crazy selector to handle odd field errors in ie6/7
				els = $('body').children().filter('.blockUI').add('body > .blockUI');
			else
				els = $el.find('>.blockUI');

			// fix cursor issue
			if ( opts.cursorReset ) {
				if ( els.length > 1 )
					els[1].style.cursor = opts.cursorReset;
				if ( els.length > 2 )
					els[2].style.cursor = opts.cursorReset;
			}

			if (full)
				pageBlock = pageBlockEls = null;

			if (opts.fadeOut) {
				els.fadeOut(opts.fadeOut);
				setTimeout(function() { reset(els,data,opts,el); }, opts.fadeOut);
			}
			else
				reset(els, data, opts, el);
		}

		// move blocking element back into the DOM where it started
		function reset(els,data,opts,el) {
			els.each(function(i,o) {
				// remove via DOM calls so we don't lose event handlers
				if (this.parentNode)
					this.parentNode.removeChild(this);
			});

			if (data && data.el) {
				data.el.style.display = data.display;
				data.el.style.position = data.position;
				if (data.parent)
					data.parent.appendChild(data.el);
				$(el).removeData('blockUI.history');
			}

			if (typeof opts.onUnblock == 'function')
				opts.onUnblock(el,opts);

			// fix issue in Safari 6 where block artifacts remain until reflow
			var body = $(document.body), w = body.width(), cssW = body[0].style.width;
			body.width(w-1).width(w);
			body[0].style.width = cssW;
		}

		// bind/unbind the handler
		function bind(b, el, opts) {
			var full = el == window, $el = $(el);

			// don't bother unbinding if there is nothing to unbind
			if (!b && (full && !pageBlock || !full && !$el.data('blockUI.isBlocked')))
				return;

			$el.data('blockUI.isBlocked', b);

			// don't bind events when overlay is not in use or if bindEvents is false
			if (!opts.bindEvents || (b && !opts.showOverlay))
				return;

			// bind anchors and inputs for mouse and key events
			var events = 'mousedown mouseup keydown keypress keyup touchstart touchend touchmove';
			if (b)
				$(document).bind(events, opts, handler);
			else
				$(document).unbind(events, handler);

		// former impl...
		//		var $e = $('a,:input');
		//		b ? $e.bind(events, opts, handler) : $e.unbind(events, handler);
		}

		// event handler to suppress keyboard/mouse events when blocking
		function handler(e) {
			// allow tab navigation (conditionally)
			if (e.keyCode && e.keyCode == 9) {
				if (pageBlock && e.data.constrainTabKey) {
					var els = pageBlockEls;
					var fwd = !e.shiftKey && e.target === els[els.length-1];
					var back = e.shiftKey && e.target === els[0];
					if (fwd || back) {
						setTimeout(function(){focus(back);},10);
						return false;
					}
				}
			}
			var opts = e.data;
			var target = $(e.target);
			if (target.hasClass('blockOverlay') && opts.onOverlayClick)
				opts.onOverlayClick();

			// allow events within the message content
			if (target.parents('div.' + opts.blockMsgClass).length > 0)
				return true;

			// allow events for content that is not being blocked
			return target.parents().children().filter('div.blockUI').length === 0;
		}

		function focus(back) {
			if (!pageBlockEls)
				return;
			var e = pageBlockEls[back===true ? pageBlockEls.length-1 : 0];
			if (e)
				e.focus();
		}

		function center(el, x, y) {
			var p = el.parentNode, s = el.style;
			var l = ((p.offsetWidth - el.offsetWidth)/2) - sz(p,'borderLeftWidth');
			var t = ((p.offsetHeight - el.offsetHeight)/2) - sz(p,'borderTopWidth');
			if (x) s.left = l > 0 ? (l+'px') : '0';
			if (y) s.top  = t > 0 ? (t+'px') : '0';
		}

		function sz(el, p) {
			return parseInt($.css(el,p),10)||0;
		}

	}


	/*global define:true */
	if (typeof define === 'function' && define.amd && define.amd.jQuery) {
		define(['jquery'], setup);
	} else {
		setup(jQuery);
	}

})();

(function($) {
	$.fn.extend({
		match$ToHtml: function (option){
			var msgObj = $(this);
			var contents = $(".summary",msgObj);
			contents.each(function (index,element){
				var rContent = $(this).html();
				var regx = /\$.*?\(([a-zA-Z]+)\d+\)\$/g;
				//正则查找“$竞彩足球 周二003 横滨水手 VS 全北现代(JZ1404152003)$”格式
				var rs = rContent.match(regx);
				if (rs) {
					for (var i = 0; i < rs.length; i++) {
						var index = rs[i].indexOf("(JZ");
						if (-1 != index) {
							var end = rs[i].indexOf(")");
							var matchId = rs[i].substring(index + 3, end);
							var t = '<a href="http://www.davcai.com/matches/JCZQ/20'
									+ matchId
									+ '" target="_blank"  _matchFloatCardType="JCZQ" _matchId="20' + matchId + '">'
									+ rs[i] + '</a>';
							rContent = rContent.replace(rs[i], t);
							rContent = rContent.replace("(JZ" + matchId + ")","");
							continue;
						}
						
						index = rs[i].indexOf("(JL");
						if (-1 != index) {
							var matchId = rs[i].substring(index + 3, rs[i].indexOf(")"));
							var t = '<a href="http://www.davcai.com/matches/JCLQ/20'
									+ matchId
									+ '" target="_blank" _matchFloatCardType="JCLQ" _matchId="20' + matchId + '">'
									+ rs[i] + '</a>';
							rContent = rContent.replace(rs[i], t);
							rContent = rContent.replace("(JL" + matchId + ")","");
							continue;
						}
						
						index = rs[i].indexOf("(BD");
						if (-1 != index) {
							var matchId = rs[i].substring(index + 3, rs[i].indexOf(")"));
							var t = '<a href="http://www.davcai.com/matches/BJDC/'
								+ matchId
								+ '" target="_blank" _matchFloatCardType="BJDC" _matchId="' + matchId + '">'
								+ rs[i] + '</a>';
							rContent = rContent.replace(rs[i], t);
							rContent = rContent.replace("(BD" + matchId + ")","");
							continue;
						}
						
						index = rs[i].indexOf("(CZ");
						if (-1 != index) {
							var matchId = rs[i].substring(index + 3, rs[i].indexOf(")"));
							var id = matchId.substring(0,5);
							var mid = matchId.substring(5,6);
							var end = matchId.substring(6);
							var s;
							if(mid == '0'){
								s = '24_ZC_14' + end;
							} else {
								s = '24_ZC_14' + mid  + end;
							}
							s = id + s;
							var t = '<a href="http://www.davcai.com/matches/CTZC/'
									+ s
									+ '" target="_blank"  _matchFloatCardType="CTZC"  _matchId="' + s + '">'
									+ rs[i] + '</a>';
							rContent = rContent.replace(rs[i], t);
							rContent = rContent.replace("(CZ" + matchId + ")","");
							continue;
						}
					}
				}
				$(this).html(rContent);
			});
		}
	});
})(jQuery);
/**placeholder Hack*/
$(document).ready(function() {
	var inputArr = $('.placeholderHack');
	if(null != inputArr && inputArr.length > 0) {
		$.each(inputArr, function(i, n) {
			var input = $(n),
			defaultValue = input.attr('value'),
			text = input.attr('placeholder'),
			supportPlaceholder = 'placeholder' in document.createElement('input');
			placeholder = function(input,text,defaultValue) {
				input.css({color:'graytext'});
				if (defaultValue == '') {
					input.attr('value',text);
				}
				input.focus(function(){
					if(input.attr('value') === text){
						input.attr('value','');
						input.css({color:''});
					}
				});
				input.keydown(function(){
					if(input.attr('value') === text){
						input.attr('value','');
					}
				});
				input.blur(function(){
					if(input.attr('value') === ''){
						input.attr('value',text);
						input.css({color:'graytext'});
					}
				});
			};
			if (!supportPlaceholder && input.attr('type') === 'text' && text) {
				placeholder(input,text,defaultValue);
			}
		});
	}
});

/*!
 * Bootstrap v3.0.2 by @fat and @mdo
 * Copyright 2013 Twitter, Inc.
 * Licensed under http://www.apache.org/licenses/LICENSE-2.0
 *
 * Designed and built with all the love in the world by @mdo and @fat.
 */

if("undefined"==typeof jQuery)throw new Error("Bootstrap requires jQuery");+function(a){"use strict";function b(){var a=document.createElement("bootstrap"),b={WebkitTransition:"webkitTransitionEnd",MozTransition:"transitionend",OTransition:"oTransitionEnd otransitionend",transition:"transitionend"};for(var c in b)if(void 0!==a.style[c])return{end:b[c]}}a.fn.emulateTransitionEnd=function(b){var c=!1,d=this;a(this).one(a.support.transition.end,function(){c=!0});var e=function(){c||a(d).trigger(a.support.transition.end)};return setTimeout(e,b),this},a(function(){a.support.transition=b()})}(jQuery),+function(a){"use strict";var b='[data-dismiss="alert"]',c=function(c){a(c).on("click",b,this.close)};c.prototype.close=function(b){function c(){f.trigger("closed.bs.alert").remove()}var d=a(this),e=d.attr("data-target");e||(e=d.attr("href"),e=e&&e.replace(/.*(?=#[^\s]*$)/,""));var f=a(e);b&&b.preventDefault(),f.length||(f=d.hasClass("alert")?d:d.parent()),f.trigger(b=a.Event("close.bs.alert")),b.isDefaultPrevented()||(f.removeClass("in"),a.support.transition&&f.hasClass("fade")?f.one(a.support.transition.end,c).emulateTransitionEnd(150):c())};var d=a.fn.alert;a.fn.alert=function(b){return this.each(function(){var d=a(this),e=d.data("bs.alert");e||d.data("bs.alert",e=new c(this)),"string"==typeof b&&e[b].call(d)})},a.fn.alert.Constructor=c,a.fn.alert.noConflict=function(){return a.fn.alert=d,this},a(document).on("click.bs.alert.data-api",b,c.prototype.close)}(jQuery),+function(a){"use strict";var b=function(c,d){this.$element=a(c),this.options=a.extend({},b.DEFAULTS,d)};b.DEFAULTS={loadingText:"loading..."},b.prototype.setState=function(a){var b="disabled",c=this.$element,d=c.is("input")?"val":"html",e=c.data();a+="Text",e.resetText||c.data("resetText",c[d]()),c[d](e[a]||this.options[a]),setTimeout(function(){"loadingText"==a?c.addClass(b).attr(b,b):c.removeClass(b).removeAttr(b)},0)},b.prototype.toggle=function(){var a=this.$element.closest('[data-toggle="buttons"]');if(a.length){var b=this.$element.find("input").prop("checked",!this.$element.hasClass("active")).trigger("change");"radio"===b.prop("type")&&a.find(".active").removeClass("active")}this.$element.toggleClass("active")};var c=a.fn.button;a.fn.button=function(c){return this.each(function(){var d=a(this),e=d.data("bs.button"),f="object"==typeof c&&c;e||d.data("bs.button",e=new b(this,f)),"toggle"==c?e.toggle():c&&e.setState(c)})},a.fn.button.Constructor=b,a.fn.button.noConflict=function(){return a.fn.button=c,this},a(document).on("click.bs.button.data-api","[data-toggle^=button]",function(b){var c=a(b.target);c.hasClass("btn")||(c=c.closest(".btn")),c.button("toggle"),b.preventDefault()})}(jQuery),+function(a){"use strict";var b=function(b,c){this.$element=a(b),this.$indicators=this.$element.find(".carousel-indicators"),this.options=c,this.paused=this.sliding=this.interval=this.$active=this.$items=null,"hover"==this.options.pause&&this.$element.on("mouseenter",a.proxy(this.pause,this)).on("mouseleave",a.proxy(this.cycle,this))};b.DEFAULTS={interval:5e3,pause:"hover",wrap:!0},b.prototype.cycle=function(b){return b||(this.paused=!1),this.interval&&clearInterval(this.interval),this.options.interval&&!this.paused&&(this.interval=setInterval(a.proxy(this.next,this),this.options.interval)),this},b.prototype.getActiveIndex=function(){return this.$active=this.$element.find(".item.active"),this.$items=this.$active.parent().children(),this.$items.index(this.$active)},b.prototype.to=function(b){var c=this,d=this.getActiveIndex();return b>this.$items.length-1||0>b?void 0:this.sliding?this.$element.one("slid",function(){c.to(b)}):d==b?this.pause().cycle():this.slide(b>d?"next":"prev",a(this.$items[b]))},b.prototype.pause=function(b){return b||(this.paused=!0),this.$element.find(".next, .prev").length&&a.support.transition.end&&(this.$element.trigger(a.support.transition.end),this.cycle(!0)),this.interval=clearInterval(this.interval),this},b.prototype.next=function(){return this.sliding?void 0:this.slide("next")},b.prototype.prev=function(){return this.sliding?void 0:this.slide("prev")},b.prototype.slide=function(b,c){var d=this.$element.find(".item.active"),e=c||d[b](),f=this.interval,g="next"==b?"left":"right",h="next"==b?"first":"last",i=this;if(!e.length){if(!this.options.wrap)return;e=this.$element.find(".item")[h]()}this.sliding=!0,f&&this.pause();var j=a.Event("slide.bs.carousel",{relatedTarget:e[0],direction:g});if(!e.hasClass("active")){if(this.$indicators.length&&(this.$indicators.find(".active").removeClass("active"),this.$element.one("slid",function(){var b=a(i.$indicators.children()[i.getActiveIndex()]);b&&b.addClass("active")})),a.support.transition&&this.$element.hasClass("slide")){if(this.$element.trigger(j),j.isDefaultPrevented())return;e.addClass(b),e[0].offsetWidth,d.addClass(g),e.addClass(g),d.one(a.support.transition.end,function(){e.removeClass([b,g].join(" ")).addClass("active"),d.removeClass(["active",g].join(" ")),i.sliding=!1,setTimeout(function(){i.$element.trigger("slid")},0)}).emulateTransitionEnd(600)}else{if(this.$element.trigger(j),j.isDefaultPrevented())return;d.removeClass("active"),e.addClass("active"),this.sliding=!1,this.$element.trigger("slid")}return f&&this.cycle(),this}};var c=a.fn.carousel;a.fn.carousel=function(c){return this.each(function(){var d=a(this),e=d.data("bs.carousel"),f=a.extend({},b.DEFAULTS,d.data(),"object"==typeof c&&c),g="string"==typeof c?c:f.slide;e||d.data("bs.carousel",e=new b(this,f)),"number"==typeof c?e.to(c):g?e[g]():f.interval&&e.pause().cycle()})},a.fn.carousel.Constructor=b,a.fn.carousel.noConflict=function(){return a.fn.carousel=c,this},a(document).on("click.bs.carousel.data-api","[data-slide], [data-slide-to]",function(b){var c,d=a(this),e=a(d.attr("data-target")||(c=d.attr("href"))&&c.replace(/.*(?=#[^\s]+$)/,"")),f=a.extend({},e.data(),d.data()),g=d.attr("data-slide-to");g&&(f.interval=!1),e.carousel(f),(g=d.attr("data-slide-to"))&&e.data("bs.carousel").to(g),b.preventDefault()}),a(window).on("load",function(){a('[data-ride="carousel"]').each(function(){var b=a(this);b.carousel(b.data())})})}(jQuery),+function(a){"use strict";var b=function(c,d){this.$element=a(c),this.options=a.extend({},b.DEFAULTS,d),this.transitioning=null,this.options.parent&&(this.$parent=a(this.options.parent)),this.options.toggle&&this.toggle()};b.DEFAULTS={toggle:!0},b.prototype.dimension=function(){var a=this.$element.hasClass("width");return a?"width":"height"},b.prototype.show=function(){if(!this.transitioning&&!this.$element.hasClass("in")){var b=a.Event("show.bs.collapse");if(this.$element.trigger(b),!b.isDefaultPrevented()){var c=this.$parent&&this.$parent.find("> .panel > .in");if(c&&c.length){var d=c.data("bs.collapse");if(d&&d.transitioning)return;c.collapse("hide"),d||c.data("bs.collapse",null)}var e=this.dimension();this.$element.removeClass("collapse").addClass("collapsing")[e](0),this.transitioning=1;var f=function(){this.$element.removeClass("collapsing").addClass("in")[e]("auto"),this.transitioning=0,this.$element.trigger("shown.bs.collapse")};if(!a.support.transition)return f.call(this);var g=a.camelCase(["scroll",e].join("-"));this.$element.one(a.support.transition.end,a.proxy(f,this)).emulateTransitionEnd(350)[e](this.$element[0][g])}}},b.prototype.hide=function(){if(!this.transitioning&&this.$element.hasClass("in")){var b=a.Event("hide.bs.collapse");if(this.$element.trigger(b),!b.isDefaultPrevented()){var c=this.dimension();this.$element[c](this.$element[c]())[0].offsetHeight,this.$element.addClass("collapsing").removeClass("collapse").removeClass("in"),this.transitioning=1;var d=function(){this.transitioning=0,this.$element.trigger("hidden.bs.collapse").removeClass("collapsing").addClass("collapse")};return a.support.transition?(this.$element[c](0).one(a.support.transition.end,a.proxy(d,this)).emulateTransitionEnd(350),void 0):d.call(this)}}},b.prototype.toggle=function(){this[this.$element.hasClass("in")?"hide":"show"]()};var c=a.fn.collapse;a.fn.collapse=function(c){return this.each(function(){var d=a(this),e=d.data("bs.collapse"),f=a.extend({},b.DEFAULTS,d.data(),"object"==typeof c&&c);e||d.data("bs.collapse",e=new b(this,f)),"string"==typeof c&&e[c]()})},a.fn.collapse.Constructor=b,a.fn.collapse.noConflict=function(){return a.fn.collapse=c,this},a(document).on("click.bs.collapse.data-api","[data-toggle=collapse]",function(b){var c,d=a(this),e=d.attr("data-target")||b.preventDefault()||(c=d.attr("href"))&&c.replace(/.*(?=#[^\s]+$)/,""),f=a(e),g=f.data("bs.collapse"),h=g?"toggle":d.data(),i=d.attr("data-parent"),j=i&&a(i);g&&g.transitioning||(j&&j.find('[data-toggle=collapse][data-parent="'+i+'"]').not(d).addClass("collapsed"),d[f.hasClass("in")?"addClass":"removeClass"]("collapsed")),f.collapse(h)})}(jQuery),+function(a){"use strict";function b(){a(d).remove(),a(e).each(function(b){var d=c(a(this));d.hasClass("open")&&(d.trigger(b=a.Event("hide.bs.dropdown")),b.isDefaultPrevented()||d.removeClass("open").trigger("hidden.bs.dropdown"))})}function c(b){var c=b.attr("data-target");c||(c=b.attr("href"),c=c&&/#/.test(c)&&c.replace(/.*(?=#[^\s]*$)/,""));var d=c&&a(c);return d&&d.length?d:b.parent()}var d=".dropdown-backdrop",e="[data-toggle=dropdown]",f=function(b){a(b).on("click.bs.dropdown",this.toggle)};f.prototype.toggle=function(d){var e=a(this);if(!e.is(".disabled, :disabled")){var f=c(e),g=f.hasClass("open");if(b(),!g){if("ontouchstart"in document.documentElement&&!f.closest(".navbar-nav").length&&a('<div class="dropdown-backdrop"/>').insertAfter(a(this)).on("click",b),f.trigger(d=a.Event("show.bs.dropdown")),d.isDefaultPrevented())return;f.toggleClass("open").trigger("shown.bs.dropdown"),e.focus()}return!1}},f.prototype.keydown=function(b){if(/(38|40|27)/.test(b.keyCode)){var d=a(this);if(b.preventDefault(),b.stopPropagation(),!d.is(".disabled, :disabled")){var f=c(d),g=f.hasClass("open");if(!g||g&&27==b.keyCode)return 27==b.which&&f.find(e).focus(),d.click();var h=a("[role=menu] li:not(.divider):visible a",f);if(h.length){var i=h.index(h.filter(":focus"));38==b.keyCode&&i>0&&i--,40==b.keyCode&&i<h.length-1&&i++,~i||(i=0),h.eq(i).focus()}}}};var g=a.fn.dropdown;a.fn.dropdown=function(b){return this.each(function(){var c=a(this),d=c.data("dropdown");d||c.data("dropdown",d=new f(this)),"string"==typeof b&&d[b].call(c)})},a.fn.dropdown.Constructor=f,a.fn.dropdown.noConflict=function(){return a.fn.dropdown=g,this},a(document).on("click.bs.dropdown.data-api",b).on("click.bs.dropdown.data-api",".dropdown form",function(a){a.stopPropagation()}).on("click.bs.dropdown.data-api",e,f.prototype.toggle).on("keydown.bs.dropdown.data-api",e+", [role=menu]",f.prototype.keydown)}(jQuery),+function(a){"use strict";var b=function(b,c){this.options=c,this.$element=a(b),this.$backdrop=this.isShown=null,this.options.remote&&this.$element.load(this.options.remote)};b.DEFAULTS={backdrop:!0,keyboard:!0,show:!0},b.prototype.toggle=function(a){return this[this.isShown?"hide":"show"](a)},b.prototype.show=function(b){var c=this,d=a.Event("show.bs.modal",{relatedTarget:b});this.$element.trigger(d),this.isShown||d.isDefaultPrevented()||(this.isShown=!0,this.escape(),this.$element.on("click.dismiss.modal",'[data-dismiss="modal"]',a.proxy(this.hide,this)),this.backdrop(function(){var d=a.support.transition&&c.$element.hasClass("fade");c.$element.parent().length||c.$element.appendTo(document.body),c.$element.show(),d&&c.$element[0].offsetWidth,c.$element.addClass("in").attr("aria-hidden",!1),c.enforceFocus();var e=a.Event("shown.bs.modal",{relatedTarget:b});d?c.$element.find(".modal-dialog").one(a.support.transition.end,function(){c.$element.focus().trigger(e)}).emulateTransitionEnd(300):c.$element.focus().trigger(e)}))},b.prototype.hide=function(b){b&&b.preventDefault(),b=a.Event("hide.bs.modal"),this.$element.trigger(b),this.isShown&&!b.isDefaultPrevented()&&(this.isShown=!1,this.escape(),a(document).off("focusin.bs.modal"),this.$element.removeClass("in").attr("aria-hidden",!0).off("click.dismiss.modal"),a.support.transition&&this.$element.hasClass("fade")?this.$element.one(a.support.transition.end,a.proxy(this.hideModal,this)).emulateTransitionEnd(300):this.hideModal())},b.prototype.enforceFocus=function(){a(document).off("focusin.bs.modal").on("focusin.bs.modal",a.proxy(function(a){this.$element[0]===a.target||this.$element.has(a.target).length||this.$element.focus()},this))},b.prototype.escape=function(){this.isShown&&this.options.keyboard?this.$element.on("keyup.dismiss.bs.modal",a.proxy(function(a){27==a.which&&this.hide()},this)):this.isShown||this.$element.off("keyup.dismiss.bs.modal")},b.prototype.hideModal=function(){var a=this;this.$element.hide(),this.backdrop(function(){a.removeBackdrop(),a.$element.trigger("hidden.bs.modal")})},b.prototype.removeBackdrop=function(){this.$backdrop&&this.$backdrop.remove(),this.$backdrop=null},b.prototype.backdrop=function(b){var c=this.$element.hasClass("fade")?"fade":"";if(this.isShown&&this.options.backdrop){var d=a.support.transition&&c;if(this.$backdrop=a('<div class="modal-backdrop '+c+'" />').appendTo(document.body),this.$element.on("click.dismiss.modal",a.proxy(function(a){a.target===a.currentTarget&&("static"==this.options.backdrop?this.$element[0].focus.call(this.$element[0]):this.hide.call(this))},this)),d&&this.$backdrop[0].offsetWidth,this.$backdrop.addClass("in"),!b)return;d?this.$backdrop.one(a.support.transition.end,b).emulateTransitionEnd(150):b()}else!this.isShown&&this.$backdrop?(this.$backdrop.removeClass("in"),a.support.transition&&this.$element.hasClass("fade")?this.$backdrop.one(a.support.transition.end,b).emulateTransitionEnd(150):b()):b&&b()};var c=a.fn.modal;a.fn.modal=function(c,d){return this.each(function(){var e=a(this),f=e.data("bs.modal"),g=a.extend({},b.DEFAULTS,e.data(),"object"==typeof c&&c);f||e.data("bs.modal",f=new b(this,g)),"string"==typeof c?f[c](d):g.show&&f.show(d)})},a.fn.modal.Constructor=b,a.fn.modal.noConflict=function(){return a.fn.modal=c,this},a(document).on("click.bs.modal.data-api",'[data-toggle="modal"]',function(b){var c=a(this),d=c.attr("href"),e=a(c.attr("data-target")||d&&d.replace(/.*(?=#[^\s]+$)/,"")),f=e.data("modal")?"toggle":a.extend({remote:!/#/.test(d)&&d},e.data(),c.data());b.preventDefault(),e.modal(f,this).one("hide",function(){c.is(":visible")&&c.focus()})}),a(document).on("show.bs.modal",".modal",function(){a(document.body).addClass("modal-open")}).on("hidden.bs.modal",".modal",function(){a(document.body).removeClass("modal-open")})}(jQuery),+function(a){"use strict";var b=function(a,b){this.type=this.options=this.enabled=this.timeout=this.hoverState=this.$element=null,this.init("tooltip",a,b)};b.DEFAULTS={animation:!0,placement:"top",selector:!1,template:'<div class="tooltip"><div class="tooltip-arrow"></div><div class="tooltip-inner"></div></div>',trigger:"hover focus",title:"",delay:0,html:!1,container:!1},b.prototype.init=function(b,c,d){this.enabled=!0,this.type=b,this.$element=a(c),this.options=this.getOptions(d);for(var e=this.options.trigger.split(" "),f=e.length;f--;){var g=e[f];if("click"==g)this.$element.on("click."+this.type,this.options.selector,a.proxy(this.toggle,this));else if("manual"!=g){var h="hover"==g?"mouseenter":"focus",i="hover"==g?"mouseleave":"blur";this.$element.on(h+"."+this.type,this.options.selector,a.proxy(this.enter,this)),this.$element.on(i+"."+this.type,this.options.selector,a.proxy(this.leave,this))}}this.options.selector?this._options=a.extend({},this.options,{trigger:"manual",selector:""}):this.fixTitle()},b.prototype.getDefaults=function(){return b.DEFAULTS},b.prototype.getOptions=function(b){return b=a.extend({},this.getDefaults(),this.$element.data(),b),b.delay&&"number"==typeof b.delay&&(b.delay={show:b.delay,hide:b.delay}),b},b.prototype.getDelegateOptions=function(){var b={},c=this.getDefaults();return this._options&&a.each(this._options,function(a,d){c[a]!=d&&(b[a]=d)}),b},b.prototype.enter=function(b){var c=b instanceof this.constructor?b:a(b.currentTarget)[this.type](this.getDelegateOptions()).data("bs."+this.type);return clearTimeout(c.timeout),c.hoverState="in",c.options.delay&&c.options.delay.show?(c.timeout=setTimeout(function(){"in"==c.hoverState&&c.show()},c.options.delay.show),void 0):c.show()},b.prototype.leave=function(b){var c=b instanceof this.constructor?b:a(b.currentTarget)[this.type](this.getDelegateOptions()).data("bs."+this.type);return clearTimeout(c.timeout),c.hoverState="out",c.options.delay&&c.options.delay.hide?(c.timeout=setTimeout(function(){"out"==c.hoverState&&c.hide()},c.options.delay.hide),void 0):c.hide()},b.prototype.show=function(){var b=a.Event("show.bs."+this.type);if(this.hasContent()&&this.enabled){if(this.$element.trigger(b),b.isDefaultPrevented())return;var c=this.tip();this.setContent(),this.options.animation&&c.addClass("fade");var d="function"==typeof this.options.placement?this.options.placement.call(this,c[0],this.$element[0]):this.options.placement,e=/\s?auto?\s?/i,f=e.test(d);f&&(d=d.replace(e,"")||"top"),c.detach().css({top:0,left:0,display:"block"}).addClass(d),this.options.container?c.appendTo(this.options.container):c.insertAfter(this.$element);var g=this.getPosition(),h=c[0].offsetWidth,i=c[0].offsetHeight;if(f){var j=this.$element.parent(),k=d,l=document.documentElement.scrollTop||document.body.scrollTop,m="body"==this.options.container?window.innerWidth:j.outerWidth(),n="body"==this.options.container?window.innerHeight:j.outerHeight(),o="body"==this.options.container?0:j.offset().left;d="bottom"==d&&g.top+g.height+i-l>n?"top":"top"==d&&g.top-l-i<0?"bottom":"right"==d&&g.right+h>m?"left":"left"==d&&g.left-h<o?"right":d,c.removeClass(k).addClass(d)}var p=this.getCalculatedOffset(d,g,h,i);this.applyPlacement(p,d),this.$element.trigger("shown.bs."+this.type)}},b.prototype.applyPlacement=function(a,b){var c,d=this.tip(),e=d[0].offsetWidth,f=d[0].offsetHeight,g=parseInt(d.css("margin-top"),10),h=parseInt(d.css("margin-left"),10);isNaN(g)&&(g=0),isNaN(h)&&(h=0),a.top=a.top+g,a.left=a.left+h,d.offset(a).addClass("in");var i=d[0].offsetWidth,j=d[0].offsetHeight;if("top"==b&&j!=f&&(c=!0,a.top=a.top+f-j),/bottom|top/.test(b)){var k=0;a.left<0&&(k=-2*a.left,a.left=0,d.offset(a),i=d[0].offsetWidth,j=d[0].offsetHeight),this.replaceArrow(k-e+i,i,"left")}else this.replaceArrow(j-f,j,"top");c&&d.offset(a)},b.prototype.replaceArrow=function(a,b,c){this.arrow().css(c,a?50*(1-a/b)+"%":"")},b.prototype.setContent=function(){var a=this.tip(),b=this.getTitle();a.find(".tooltip-inner")[this.options.html?"html":"text"](b),a.removeClass("fade in top bottom left right")},b.prototype.hide=function(){function b(){"in"!=c.hoverState&&d.detach()}var c=this,d=this.tip(),e=a.Event("hide.bs."+this.type);return this.$element.trigger(e),e.isDefaultPrevented()?void 0:(d.removeClass("in"),a.support.transition&&this.$tip.hasClass("fade")?d.one(a.support.transition.end,b).emulateTransitionEnd(150):b(),this.$element.trigger("hidden.bs."+this.type),this)},b.prototype.fixTitle=function(){var a=this.$element;(a.attr("title")||"string"!=typeof a.attr("data-original-title"))&&a.attr("data-original-title",a.attr("title")||"").attr("title","")},b.prototype.hasContent=function(){return this.getTitle()},b.prototype.getPosition=function(){var b=this.$element[0];return a.extend({},"function"==typeof b.getBoundingClientRect?b.getBoundingClientRect():{width:b.offsetWidth,height:b.offsetHeight},this.$element.offset())},b.prototype.getCalculatedOffset=function(a,b,c,d){return"bottom"==a?{top:b.top+b.height,left:b.left+b.width/2-c/2}:"top"==a?{top:b.top-d,left:b.left+b.width/2-c/2}:"left"==a?{top:b.top+b.height/2-d/2,left:b.left-c}:{top:b.top+b.height/2-d/2,left:b.left+b.width}},b.prototype.getTitle=function(){var a,b=this.$element,c=this.options;return a=b.attr("data-original-title")||("function"==typeof c.title?c.title.call(b[0]):c.title)},b.prototype.tip=function(){return this.$tip=this.$tip||a(this.options.template)},b.prototype.arrow=function(){return this.$arrow=this.$arrow||this.tip().find(".tooltip-arrow")},b.prototype.validate=function(){this.$element[0].parentNode||(this.hide(),this.$element=null,this.options=null)},b.prototype.enable=function(){this.enabled=!0},b.prototype.disable=function(){this.enabled=!1},b.prototype.toggleEnabled=function(){this.enabled=!this.enabled},b.prototype.toggle=function(b){var c=b?a(b.currentTarget)[this.type](this.getDelegateOptions()).data("bs."+this.type):this;c.tip().hasClass("in")?c.leave(c):c.enter(c)},b.prototype.destroy=function(){this.hide().$element.off("."+this.type).removeData("bs."+this.type)};var c=a.fn.tooltip;a.fn.tooltip=function(c){return this.each(function(){var d=a(this),e=d.data("bs.tooltip"),f="object"==typeof c&&c;e||d.data("bs.tooltip",e=new b(this,f)),"string"==typeof c&&e[c]()})},a.fn.tooltip.Constructor=b,a.fn.tooltip.noConflict=function(){return a.fn.tooltip=c,this}}(jQuery),+function(a){"use strict";var b=function(a,b){this.init("popover",a,b)};if(!a.fn.tooltip)throw new Error("Popover requires tooltip.js");b.DEFAULTS=a.extend({},a.fn.tooltip.Constructor.DEFAULTS,{placement:"right",trigger:"click",content:"",template:'<div class="popover"><div class="arrow"></div><h3 class="popover-title"></h3><div class="popover-content"></div></div>'}),b.prototype=a.extend({},a.fn.tooltip.Constructor.prototype),b.prototype.constructor=b,b.prototype.getDefaults=function(){return b.DEFAULTS},b.prototype.setContent=function(){var a=this.tip(),b=this.getTitle(),c=this.getContent();a.find(".popover-title")[this.options.html?"html":"text"](b),a.find(".popover-content")[this.options.html?"html":"text"](c),a.removeClass("fade top bottom left right in"),a.find(".popover-title").html()||a.find(".popover-title").hide()},b.prototype.hasContent=function(){return this.getTitle()||this.getContent()},b.prototype.getContent=function(){var a=this.$element,b=this.options;return a.attr("data-content")||("function"==typeof b.content?b.content.call(a[0]):b.content)},b.prototype.arrow=function(){return this.$arrow=this.$arrow||this.tip().find(".arrow")},b.prototype.tip=function(){return this.$tip||(this.$tip=a(this.options.template)),this.$tip};var c=a.fn.popover;a.fn.popover=function(c){return this.each(function(){var d=a(this),e=d.data("bs.popover"),f="object"==typeof c&&c;e||d.data("bs.popover",e=new b(this,f)),"string"==typeof c&&e[c]()})},a.fn.popover.Constructor=b,a.fn.popover.noConflict=function(){return a.fn.popover=c,this}}(jQuery),+function(a){"use strict";function b(c,d){var e,f=a.proxy(this.process,this);this.$element=a(c).is("body")?a(window):a(c),this.$body=a("body"),this.$scrollElement=this.$element.on("scroll.bs.scroll-spy.data-api",f),this.options=a.extend({},b.DEFAULTS,d),this.selector=(this.options.target||(e=a(c).attr("href"))&&e.replace(/.*(?=#[^\s]+$)/,"")||"")+" .nav li > a",this.offsets=a([]),this.targets=a([]),this.activeTarget=null,this.refresh(),this.process()}b.DEFAULTS={offset:10},b.prototype.refresh=function(){var b=this.$element[0]==window?"offset":"position";this.offsets=a([]),this.targets=a([]);var c=this;this.$body.find(this.selector).map(function(){var d=a(this),e=d.data("target")||d.attr("href"),f=/^#\w/.test(e)&&a(e);return f&&f.length&&[[f[b]().top+(!a.isWindow(c.$scrollElement.get(0))&&c.$scrollElement.scrollTop()),e]]||null}).sort(function(a,b){return a[0]-b[0]}).each(function(){c.offsets.push(this[0]),c.targets.push(this[1])})},b.prototype.process=function(){var a,b=this.$scrollElement.scrollTop()+this.options.offset,c=this.$scrollElement[0].scrollHeight||this.$body[0].scrollHeight,d=c-this.$scrollElement.height(),e=this.offsets,f=this.targets,g=this.activeTarget;if(b>=d)return g!=(a=f.last()[0])&&this.activate(a);for(a=e.length;a--;)g!=f[a]&&b>=e[a]&&(!e[a+1]||b<=e[a+1])&&this.activate(f[a])},b.prototype.activate=function(b){this.activeTarget=b,a(this.selector).parents(".active").removeClass("active");var c=this.selector+'[data-target="'+b+'"],'+this.selector+'[href="'+b+'"]',d=a(c).parents("li").addClass("active");d.parent(".dropdown-menu").length&&(d=d.closest("li.dropdown").addClass("active")),d.trigger("activate")};var c=a.fn.scrollspy;a.fn.scrollspy=function(c){return this.each(function(){var d=a(this),e=d.data("bs.scrollspy"),f="object"==typeof c&&c;e||d.data("bs.scrollspy",e=new b(this,f)),"string"==typeof c&&e[c]()})},a.fn.scrollspy.Constructor=b,a.fn.scrollspy.noConflict=function(){return a.fn.scrollspy=c,this},a(window).on("load",function(){a('[data-spy="scroll"]').each(function(){var b=a(this);b.scrollspy(b.data())})})}(jQuery),+function(a){"use strict";var b=function(b){this.element=a(b)};b.prototype.show=function(){var b=this.element,c=b.closest("ul:not(.dropdown-menu)"),d=b.data("target");if(d||(d=b.attr("href"),d=d&&d.replace(/.*(?=#[^\s]*$)/,"")),!b.parent("li").hasClass("active")){var e=c.find(".active:last a")[0],f=a.Event("show.bs.tab",{relatedTarget:e});if(b.trigger(f),!f.isDefaultPrevented()){var g=a(d);this.activate(b.parent("li"),c),this.activate(g,g.parent(),function(){b.trigger({type:"shown.bs.tab",relatedTarget:e})})}}},b.prototype.activate=function(b,c,d){function e(){f.removeClass("active").find("> .dropdown-menu > .active").removeClass("active"),b.addClass("active"),g?(b[0].offsetWidth,b.addClass("in")):b.removeClass("fade"),b.parent(".dropdown-menu")&&b.closest("li.dropdown").addClass("active"),d&&d()}var f=c.find("> .active"),g=d&&a.support.transition&&f.hasClass("fade");g?f.one(a.support.transition.end,e).emulateTransitionEnd(150):e(),f.removeClass("in")};var c=a.fn.tab;a.fn.tab=function(c){return this.each(function(){var d=a(this),e=d.data("bs.tab");e||d.data("bs.tab",e=new b(this)),"string"==typeof c&&e[c]()})},a.fn.tab.Constructor=b,a.fn.tab.noConflict=function(){return a.fn.tab=c,this},a(document).on("click.bs.tab.data-api",'[data-toggle="tab"], [data-toggle="pill"]',function(b){b.preventDefault(),a(this).tab("show")})}(jQuery),+function(a){"use strict";var b=function(c,d){this.options=a.extend({},b.DEFAULTS,d),this.$window=a(window).on("scroll.bs.affix.data-api",a.proxy(this.checkPosition,this)).on("click.bs.affix.data-api",a.proxy(this.checkPositionWithEventLoop,this)),this.$element=a(c),this.affixed=this.unpin=null,this.checkPosition()};b.RESET="affix affix-top affix-bottom",b.DEFAULTS={offset:0},b.prototype.checkPositionWithEventLoop=function(){setTimeout(a.proxy(this.checkPosition,this),1)},b.prototype.checkPosition=function(){if(this.$element.is(":visible")){var c=a(document).height(),d=this.$window.scrollTop(),e=this.$element.offset(),f=this.options.offset,g=f.top,h=f.bottom;"object"!=typeof f&&(h=g=f),"function"==typeof g&&(g=f.top()),"function"==typeof h&&(h=f.bottom());var i=null!=this.unpin&&d+this.unpin<=e.top?!1:null!=h&&e.top+this.$element.height()>=c-h?"bottom":null!=g&&g>=d?"top":!1;this.affixed!==i&&(this.unpin&&this.$element.css("top",""),this.affixed=i,this.unpin="bottom"==i?e.top-d:null,this.$element.removeClass(b.RESET).addClass("affix"+(i?"-"+i:"")),"bottom"==i&&this.$element.offset({top:document.body.offsetHeight-h-this.$element.height()}))}};var c=a.fn.affix;a.fn.affix=function(c){return this.each(function(){var d=a(this),e=d.data("bs.affix"),f="object"==typeof c&&c;e||d.data("bs.affix",e=new b(this,f)),"string"==typeof c&&e[c]()})},a.fn.affix.Constructor=b,a.fn.affix.noConflict=function(){return a.fn.affix=c,this},a(window).on("load",function(){a('[data-spy="affix"]').each(function(){var b=a(this),c=b.data();c.offset=c.offset||{},c.offsetBottom&&(c.offset.bottom=c.offsetBottom),c.offsetTop&&(c.offset.top=c.offsetTop),b.affix(c)})})}(jQuery);
(function($){
    $.extend({
        format: function(source, params) {
        	if ( arguments.length == 1 ) 
        		return function() {
        			var args = $.makeArray(arguments);
        			args.unshift(source);
        			return $.format.apply( this, args );
        		};
        	if ( arguments.length > 2 && params && params.constructor != Array  ) {
        		params = $.makeArray(arguments).slice(1);
        	}
        	if (params && params.constructor != Array ) {
        		params = [ params ];
        	}
        	if(params) {
        		$.each(params, function(i, n) {
        			source = source.replace(new RegExp("\\{" + i + "\\}", "g"), n);
        		});
        	}
        	return source;
        },
        
        contain : function(array, element) {
			for (i in array) {
				if (array[i] == element) {
					return true;
				}
			}
			return false;
		},
		
        parseJSONData: function(json, success){
        	if(json.success){
        		if($.isFunction(success)){
        			success(json.data);
        		}
        	}else{
        		var data = json.data, message = '';
        		if(typeof(data.actionErrors) !== 'undefined'){
        			message += data.actionErrors.join(';');
        		}
        		if(typeof(data.fieldErrors) !== 'undefined'){
        			$.each(data.fieldErrors, function(){
        				for(var name in this){
        					message += name + ':' + this[name] + ';';
        				}
        			});
        		}
        		if(message == ''){
        			message = data;
        		}
        		alert(message);
        	}
        },
        
        BLANK_IMG : '../images/s.gif'
    });

    var playCache = [];
    $.extend($.fn, {
    	
    	/**
    	 * option: {
    	 * 	   value: null  // 默认值
    	 * }
    	 * 
    	 */
    	initPlay: function(option){
    		var ctx = this, opt = option || {}, val = opt.defVal || '01_ZC_1', lid = opt.lid || 'JCZQ';
    		if(playCache.length == 0){
    			$.getJSON('/play/aj_ls', {_: new Date().getTime()}, function(json){
    				$.parseJSONData(json, function(data){
    					playCache = data;
    					var html = [], sel;
    					$.each(data, function(i, p){
    						if(p.lotteryId == lid){
    							sel = (p.id == val ? ' selected="true"': '');
    							html.push($.format('<option value="{0}"{2}>{1}</option>', p.id, p.name, sel));
    						}
    					});
    					
    					ctx.html(html.join(''));
    				});
    			});
    		} else {
    			var html = [], sel;
				$.each(playCache, function(i, p){
					if(p.lotteryId == lid){
						sel = (p.id == val ? ' selected="true"': '');
						html.push($.format('<option value="{0}"{2}>{1}</option>', p.id, p.name, sel));
					}
				});
				
				ctx.html(html.join(''));
    		}
    		return this;
    	}
    	
    });
    
    window.options = {
		datepicker: {
			dateFormat: 'yy-mm-dd',
	        changeYear: true,
	        changeMonth: true,
	        prevText: '上月',
	        nextText: '下月',
	        dayNamesMin: ['日','一','二','三','四','五','六'],
	        monthNamesShort: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月']
		}	
    };
    
    window.options.datetimepicker = $.extend({
    	timeFormat: 'hh:mm:ss',
        currentText: '当前时间',
        closeText: '确定',
        timeText: '时间：',
        hourText: '时：',
        minuteText: '分：'
	}, window.options.datepicker);
    
})(jQuery);
function loadLoginHead(weiboUser){
	var content_davcai =
		' <ul class="davcai-header-menu">'
        +	'<li><a href="http://www.davcai.com">首页</a></li>' //class="current"
        +	'<li><a href="http://www.davcai.com/recommend/category_JCZQ">推荐</a></li>'
        +	'<li><a href="http://www.davcai.com/realfollow/category_JCZQ">晒单</a></li>'
        +	'<li class="header-menu-bet">'
 	    +	'<a href="javascript:void(0);">投注</a>'
 	    +	'<img src="http://www.davcai.com/images/davcai/header/davcai-user-bg.png"  class="bet-triangle"/>'
 	    +	'<div class="header-menu-bet-list">'
 		+	'<p class="bet-list-top"></p>'
 		+	'<ul class="bet-list-middle">'
	 		+		'<li class="bet-list-middle-son">'
	 		+			'<a href="javascript:void(0);">'
	 		+				'<img src="http://www.davcai.com/images/davcai/header/jczq-lottery-logo.png" class="list-middle-son-logo"/>'
	 		+			'</a>'
	 		+			'<h4 class="list-middle-son-name">竞彩足球</h4>'
	 		+			'<p class="list-middle-son-details">'
	 		+				'<a href="http://trade.davcai.com/df/bet/jczq_80_zc_2.html">胜平负</a>&nbsp;&nbsp;'
	 		+				'<a href="http://trade.davcai.com/df/bet/jczq_01_zc_1.html">让球胜平负</a>'
	 		+			'</p>'
	 		+			'<p class="list-middle-son-details">'
	 		+				'<a href="http://trade.davcai.com/df/bet/jczq_05_zc_2.html">混合过关</a>&nbsp;'
	 		+				'<a href="http://trade.davcai.com/df/bet/jczq_02_zc_2.html">比分</a>&nbsp;'
	 		+				'<a href="http://trade.davcai.com/df/bet/jczq_03_zc_2.html">进球数</a>&nbsp;&nbsp;'
	 		+				'<a href="http://trade.davcai.com/df/bet/jczq_04_zc_2.html">半全场</a>'
	 		+			'</p>'
	 		+		'</li>'
	 		+		'<li class="bet-list-middle-son">'
	 		+			'<a href="javascript:void(0);">'
	 		+				'<img src="http://www.davcai.com/images/davcai/header/jclq-lottery-logo.png" class="list-middle-son-logo"/>'
	 		+			'</a>'
	 		+			'<h4 class="list-middle-son-name">竞彩篮球</h4>'
	 		+			'<p class="list-middle-son-details">'
	 		+				'<a href="http://trade.davcai.com/df/bet/jclq_06_lc_2.html">让分胜负</a>&nbsp;&nbsp;&nbsp;'
	 		+				'<a href="http://trade.davcai.com/df/bet/jclq_07_lc_2.html">胜负</a>&nbsp;&nbsp;&nbsp;'
	 		+				'<a href="http://trade.davcai.com/df/bet/jclq_08_lc_2.html">胜负差</a>'
	 		+			'</p>'
	 		+			'<p class="list-middle-son-details">'
	 		+				'<a href="http://trade.davcai.com/df/bet/jclq_09_lc_2.html">大小分</a>&nbsp;&nbsp;&nbsp;'
	 		+				'<a href="http://trade.davcai.com/df/bet/jclq_10_lc_2.html">混合过关</a>'
	 		+			'</p>'
	 		+		'</li>'
	 		+		'<li class="bet-list-middle-son">'
	 		+			'<a href="javascript:void(0);">'
	 		+				'<img src="http://www.davcai.com/images/davcai/header/bjdc-lottery-logo.png" class="list-middle-son-logo"/>'
	 		+			'</a>'
	 		+			'<h4 class="list-middle-son-name">北京单场</h4>'
	 		+			'<p class="list-middle-son-details">'
	 		+				'<a href="http://trade.davcai.com/df/bet/bjdc_spf.html">胜平负</a>&nbsp;'
	 		+				'<a href="http://trade.davcai.com/df/bet/bjdc_bf.html">比分</a>&nbsp;'
	 		+				'<a href="http://trade.davcai.com/df/bet/bjdc_bqc.html">半全场 </a>&nbsp;'
	 		+				'<a href="http://trade.davcai.com/df/bet/bjdc_jqs.html">总进球数 </a>'
	 		+			'</p>'
	 		+			'<p class="list-middle-son-details">'
	 		+				'<a href="http://trade.davcai.com/df/bet/bjdc_sxds.html">上下单双 </a>&nbsp;&nbsp;&nbsp;'
	 		+				'<a href="http://trade.davcai.com/df/bet/bjdc_sf.html">胜负过关</a>'
	 		+			'</p>'
	 		+		'</li>'
	 		+		'<li class="bet-list-middle-son">'
	 		+			'<a href="javascript:void(0);">'
	 		+				'<img src="http://www.davcai.com/images/davcai/header/ctzc-lottery-logo.png" class="list-middle-son-logo"/>'
	 		+			'</a>'
	 		+			'<h4 class="list-middle-son-name">传统足彩</h4>'
	 		+			'<p class="list-middle-son-details">'
	 		+				'<a href="http://trade.davcai.com/df/bet/ctzc_24_zc_14.html">足彩14场胜负 </a>&nbsp;&nbsp;'
	 		+				'<a href="http://trade.davcai.com/df/bet/ctzc_25_zc_r9.html">任选9场 </a><br/>'
	 		+				'<a href="http://trade.davcai.com/df/bet/ctzc_27_zc_jq.html">四场进球 </a>&nbsp;'
	 		+               '<a href="http://trade.davcai.com/df/bet/ctzc_26_zc_bq.html">六场半全 </a>'
	 		+			'</p>'
	 		+		'</li>'
	 		+		'<li class="bet-list-middle-son">'
	 		+			'<a href="javascript:void(0);">'
	 		+				'<img src="http://www.davcai.com/images/davcai/header/szc-lottery-logo.png" class="list-middle-son-logo"/>'
	 		+			'</a>'
	 		+			'<h4 class="list-middle-son-name">福彩</h4>'
	 		+			'<p class="list-middle-son-details">'
	 		+				'<a href="http://trade.davcai.com/df/bet/ssq_general.html">双色球 </a>&nbsp;&nbsp;'
	 		+			'</p>'
	 		+     	'</li>'
	 		+	'</ul>'
	 		+	'<p class="bet-list-bottom"></p>'
	 	    +	'</div>'
	 	    +'</li>'
	 	    +'<li class="header-menu-live">'
	 		+'<a href="javascript:void(0);">直播</a>'
	 		+'<img src="http://www.davcai.com/images/davcai/header/davcai-user-bg.png"  class="live-triangle"/>'
	 		+ '<div class="header-menu-live-list">'
	      	+	 '<p><a href="http://www.davcai.com/JZscore.do">竞足比分</a></p>'
	      	+    '<p><a href="http://www.davcai.com/JLscore.do">竞篮比分</a></p>'
	      	+    '<p><a href="http://www.davcai.com/CTscore.do">14场比分</a></p>'
	        +'</div>'
	 	    +'</li>'
	 	    +'<li><a href="http://www.davcai.com/find_people">找人</a></li>'
        + '</ul>'//<!------davcai-header-menu头部菜单结束---------->
        + '<div class="mail-box" >' //id="mail_box"
        +'		<div id="privateMsg">'
        +'  	</div>'
        + '		<a href="http://www.davcai.com/private_msg" target="_blank"></a>'
        + '</div>'
        + '<ul class="davcai-user" id="load_user_info"></ul>';
	$("#sync_head").append(content_davcai);
	Notification.isHaveUnreadMessage();
    var html='<li class="title">{2}</li>'
        + '<ul class="davcai-user-list">'
        +	'<li class="davcai-user-list-first">'
        +	  ' <a href="http://www.davcai.com/{0}/profile">'
        +			'<img src="{1}" class="davcai-user-list-logo">'
        +		'</a>'
        +			'<p class="davcai-user-list-name">'
        +				'<a href="javascript:void(0);">{2}</a>'
        +				'</p>'
        +				'<p class="davcai-user-list-money">'
        +	   				'<a href="javascript:void(0);">'
        +		   					'余额:{3}'
        +	    				'</a>'
        +	    			'</p>'
        +	        	'</li>'
        +	        	'<li><a href="http://www.davcai.com/{0}/edit">&nbsp;账号管理</a></li>'
        +	         	'<li><a href="http://trade.davcai.com/ac/record/bet.do">&nbsp;投注管理</a></li>'
        +	   '<li><a href="#" onclick="logout(' + "'http://www.davcai.com/index'" + ')">&nbsp;退出</a></li>'
        +	' </ul>';
		//判断昵称长度，如果过长，截取部分加...
		var nicknameByteLength = weiboUser.nickName.replace(/[^\x00-\xff]/gi, "--").length;
		if(nicknameByteLength > 16){
			weiboUser.nickName = weiboUser.nickName.substring(0,8) + "..."; 
		} 
 	html = $.format(html,weiboUser.weiboUserId,weiboUser.headImageURL,weiboUser.nickName,weiboUser.freeMoney);
 	$('#load_user_info').html(html);
 	bindDropdown();
 	
 	$(".header-menu-bet").live("mouseover",function(){
		 $(this).find('.header-menu-bet-list').show();//you can give it a speed
	}).live("mouseout",function(){
		$(this).find('.header-menu-bet-list').hide();
	});
 	
 	$(".header-menu-live").live("mouseover",function(){
		$(this).children(".header-menu-live-list").show();
	}).live("mouseout",function(){
		$(this).children(".header-menu-live-list").hide();
	});
 	$('link[href="http://www.davcai.com/css/davcai/header/header_not_login.css"]').remove();
}
function loadNotLoginHead(){
	var regist_and_login =  '<ul class="davcai-header-menu-not-login">'
							+'	<li><a href="#" onclick="toRegistPage(\'http://www.davcai.com/upload-head-img\',\'http://www.davcai.com/welcome\')">注册</a></li>'
							+'	<li><a href="http://www.davcai.com/welcome">登录</a></li>'
							+'</ul>';
	$(".davcai-header-content").append(regist_and_login);
	$('link[href="http://www.davcai.com/css/davcai/header/header.css"]').remove();
}

function loadHeader() {
	//ajax请求 判断登录状态，并设置相应的头部
	$.getJSON('http://login.davcai.com/loadmsg.do?jsonp=?', function(json){
	    if(json.success && json.data.id > 0){
	    	 //获取微博信息,并填充至html片段
	    	$.getJSON('http://login.davcai.com/ajax_get_weibouser.do?jsonp=?', function(data){
	    		if (data != null) {
	    			var weiboUser = data.weiboUser;
	    			loadLoginHead(weiboUser);
	    		} else {
	    			loadNotLoginHead();
	    		}
	    	}, 'json');
	    } else {
	    	loadNotLoginHead();
	    }
	});
}
$(document).ready(function() {
	loadHeader();
	var messageWindow = $("#messageWindow"); 				// 消息通知窗口
	var messageContent = $("#messageContent"); 				// 消息通知窗口
	
	// 弹出只有确认按钮的对话框
	$.showMessage = function showMessage(message) {
		messageContent.text(message);
		messageWindow.modal('show');
	};
});

function bindDropdown(){
	$("#load_user_info").mouseout(function(){
		$(this).children(".davcai-user-list").hide();
	}).mouseover(function(){
		$(this).children(".davcai-user-list").show();
	});
}

var showLoading = function(container) {
	container.next('.loading').show();
};

var hideLoading = function(container) {
	container.next('.loading').hide();
};
var ye_dialog = {
    Counter: 0,
    init: function(isladding/*锁定窗口时不显示loading图片，如参数为ture*/){
        if($.browser.msie && $.browser.version=='6.0'){$("select").css("visibility", "hidden");}
		if(isladding){
        var a = '<div id="ye_dialog_overlay"></div><div id="ye_dialog_window"><a id="ye_dialog_close" href="#" title="\u5173\u95ed"></a><div id="ye_dialog_title">\u8bf7\u7a0d\u5019...</div><div id="ye_dialog_body"></div></div>';
        }else{//默认锁定窗口时显示loading图片
        var a = '<div id="ye_dialog_overlay"></div><div id="ye_dialog_loading"></div><div id="ye_dialog_window"><a id="ye_dialog_close" href="#" title="\u5173\u95ed"></a><div id="ye_dialog_title">\u8bf7\u7a0d\u5019...</div><div id="ye_dialog_body"></div></div>';
		}
	   $("body").append(a);
        var b = this.closeFun ? this.closeFun : function(){
        };
        $("#ye_dialog_close").click(function(){
            b();
            ye_dialog.close();
            return false
        });
        $("#ye_dialog_overlay").show();
        $("#ye_dialog_loading").show();
        this.position();
        return this
    },
    openHtml: function(b, e, a, d, c, f){
		/*
		 (
		    b:html,
			e:title,
			a:width,
			d:height,
			c:callback,
			f:closeFun
		  )
		*/
        if (f && $.isFunction(f.closeFun)) {
            this.closeFun = f.closeFun
        }
        this.init();
        if (b != undefined) {
        	if (typeof b=='object'){
        		$("#ye_dialog_body").append(b.children());
        		this.close_fun2 = function () {
						b.append( $("#ye_dialog_body").children() ); // move elements back when you're finished
				};
        	}else {
            	$("#ye_dialog_body").html(b)
            }
        }
        this.title(e == undefined ? "\u540C\u5B66" : e);
        this.resize(a ? a : 300, d ? d : 150);
        $("#ye_dialog_loading").remove();
        $("#ye_dialog_window").show();
        if ($.isFunction(c)) {
            c()
        }
        return this
    },
    openUrl: function(c, a, e, g, scrolling){
		/*
		(
		   c:url,
		   a:width,
		   e:height,
		   g:title,
		   f:scroll
		 )
		*/
        this.init();
        var b = a != undefined ? a : 300;
        var f = e != undefined ? e : 150;
		var s = scrolling != undefined ? scrolling : 'no';
        var d = (new Date).getTime();
        if (c.indexOf("?") == -1) {
            c = c + "?_t=" + d
        }
        else {
            c = c + "&_t=" + d
        }
        this.title(g == undefined ? "\u540C\u5B66" : g);
        $("#ye_dialog_body").html('<iframe id="ye_dialog_iframe" scrolling="'+s+'" frameborder="0"></iframe>');
        $("#ye_dialog_iframe").attr("src", c);
        this.resize(b, f);
        $("#ye_dialog_loading").remove();
        $("#ye_dialog_window").show()
    },
    close: function(){
    	if (typeof this.close_fun2=='function'){
    		this.close_fun2();
    	}
        $("#ye_dialog_window").remove();
        $("#ye_dialog_overlay").remove();
        if($.browser.msie && $.browser.version=='6.0'){$("select").css("visibility", "visible");}
        return this
    },
    resize: function(a, c){//(a:width---int, b:height----int)
        var d = a ? a : 300;
        var b = c ? c : 150;
        $("#ye_dialog_window").css({
            width: d + "px",
            height: b + "px"
        });
        $("#ye_dialog_body").css("height", "99%").css("height", c - 28 + "px");
        this.position();
        return this
    },
    position: function(){
        var b = $("#ye_dialog_window").width();
        var a = $("#ye_dialog_window").height();
        $("#ye_dialog_window").css({
            marginLeft: "-" + parseInt(b / 2) + "px"
        });
        if (!($.browser.msie && $.browser.version < 7)) {
            $("#ye_dialog_window").css({
                marginTop: "-" + parseInt(a / 2) + "px"
            })
        }
        return this
    },
    title: function(a){
        if (a != undefined) {
            $("#ye_dialog_title").text(a);
            return this
        }
        else {
            return $("#ye_dialog_title").text()
        }
    }
};
// ico 1正确，2错误，3提示，4警告，5疑问
var ye_msg = {
    Counter: 0,
    open: function(e, d, c, b){//(e:html,d:timer,c:type(int),b:callback)
        $('.ye_msg_window').remove();
        var jstz = e.indexOf('<script');
        if (jstz!=-1) {
            e='正在载入中...'+e;
        }
        if($.browser.msie && $.browser.version=='6.0'){$("select").css("visibility", "hidden");}
        this.Counter++;
        var a = '<div id="ye_msg_' + this.Counter + '" class="ye_msg_window"><div class="ye_msg_wrap">' + e + "</div></div>";
        $("body").append(a);
        $("#ye_msg_" + this.Counter + " > .ye_msg_wrap").addClass("ye_msg_ico_" + c);
        this.position(this.Counter);
        if (typeof b == "function") {
            b()
        }
        if (d != undefined && d != 0) {
            $("#ye_msg_" + this.Counter + " > .ye_msg_wrap").append('<div class="ye_msg_autoclose">('+d+langjs['ye_msg_close']+")</div>");
            setTimeout('$("#ye_msg_' + this.Counter + '").remove();if($.browser.msie && $.browser.version=="6.0"){$("select").css("visibility", "visible");}', d * 1000)
        }
    },
    position: function(c){//(c:this.Counter)
        var b = $("#ye_msg_" + c).width();
        var a = $("#ye_msg_" + c).height();
        $("#ye_msg_" + c).css({
            marginLeft: "-" + parseInt(b / 2) + "px"
        })
    }
};
/**
 * Infinite Ajax Scroll, a jQuery plugin
 * Version 1.0.0
 * https://github.com/webcreate/infinite-ajax-scroll
 *
 * Copyright (c) 2011-2013 Jeroen Fiege
 * Licensed under MIT:
 * http://webcreate.nl/license
 */

(function ($) {

    'use strict';

    Date.now = Date.now || function () { return +new Date(); };

    $.ias = function (options)
    {
        // setup
        var opts             = $.extend({}, $.ias.defaults, options);
        var util             = new $.ias.util();                                // utilities module
        var paging           = new $.ias.paging(opts.scrollContainer);          // paging module
        var hist             = (opts.history ? new $.ias.history() : false);    // history module
        var _self            = this;
        var weiboUI = options.weiboUI;

        /**
         * Initialize
         *
         * - tracks scrolling through pages
         * - remembers current page with the history module
         * - setup scroll event and hides pagination element
         * - loads and scrolls to previous page when we have something in our history
         *
         * @return self
         */
        function init()
        {
            var pageNum;

            // track page number changes
            paging.onChangePage(function (pageNum, scrollOffset, pageUrl) {
                if (hist) {
                    hist.setPage(pageNum, pageUrl);
                }

                // call onPageChange event
                opts.onPageChange.call(this, pageNum, pageUrl, scrollOffset);
            });

            if (opts.triggerPageThreshold > 0) {
                // setup scroll and hide pagination
                reset();
            } else if ($(opts.next).attr('href')) {
                var curScrOffset = util.getCurrentScrollOffset(opts.scrollContainer);
                show_trigger(function () {
                    paginate(curScrOffset);
                });
            }

            // load and scroll to previous page
            if (hist && hist.havePage()) {
                stop_scroll();

                pageNum = hist.getPage();

                util.forceScrollTop(function () {
                    var curThreshold;

                    if (pageNum > 1) {
                        paginateToPage(pageNum);

                        curThreshold = get_scroll_threshold(true);
                        $('html, body').scrollTop(curThreshold);
                    }
                    else {
                        reset();
                    }
                });
            }

            return _self;
        }

        // initialize
        init();

        /**
         * Reset scrolling and hide pagination links
         *
         * @return void
         */
        function reset()
        {
            hide_pagination();

            opts.scrollContainer.scroll(scroll_handler);
        }

        /**
         * Scroll event handler
         *
         * @return void
         */
        function scroll_handler()
        {
            var curScrOffset,
                scrThreshold;

            curScrOffset = util.getCurrentScrollOffset(opts.scrollContainer);
            scrThreshold = get_scroll_threshold();

            if (curScrOffset >= scrThreshold) {
                if (get_current_page() >= opts.triggerPageThreshold) {
                    stop_scroll();
                    show_trigger(function () {
                        paginate(curScrOffset);
                    });
                }
                else {
                    paginate(curScrOffset);
                }
            }
            opts.onScroll(curScrOffset, get_current_page(), scrThreshold);
        }

        /**
         * Cancel scrolling
         *
         * @return void
         */
        function stop_scroll()
        {
            opts.scrollContainer.unbind('scroll', scroll_handler);
        }

        /**
         * Hide pagination
         *
         * @return void
         */
        function hide_pagination()
        {
            $(opts.pagination).hide();
        }

        /**
         * Get scroll threshold based on the last item element
         *
         * @param boolean pure indicates if the thresholdMargin should be applied
         * @return integer threshold
         */
        function get_scroll_threshold(pure)
        {
            var el,
                threshold;

            el = $(opts.container).find(opts.item).last();

            if (el.size() === 0) {
                return 0;
            }

            threshold = el.offset().top + el.height();

            if (!pure) {
                threshold += opts.thresholdMargin;
            }

            return threshold;
        }

        /**
         * Load the items from the next page.
         *
         * @param int      curScrOffset      current scroll offset
         * @param function onCompleteHandler callback function
         * @return void
         */
        function paginate(curScrOffset, onCompleteHandler)
        {
            var urlNextPage;

            urlNextPage = $(opts.next).attr('href');
            if (!urlNextPage) {
                if (opts.noneleft) {
                    $(opts.container).find(opts.item).last().after(opts.noneleft);
                }
                return stop_scroll();
            }

            if (opts.beforePageChange && $.isFunction(opts.beforePageChange)) {
                if (opts.beforePageChange(curScrOffset, urlNextPage) === false) {
                    return;
                }
            }

            paging.pushPages(curScrOffset, urlNextPage);

            stop_scroll();
            show_loader();

            loadItems(urlNextPage, function (data, items) {
                // call the onLoadItems callback
                var result = opts.onLoadItems.call(this, items),
                    curLastItem;

                if (result !== false) {
                    $(items).hide();  // at first, hide it so we can fade it in later

                    // insert them after the last item with a nice fadeIn effect
                    curLastItem = $(opts.container).find(opts.item).last();
                    curLastItem.after(items);
                    $(items).fadeIn();
                }

                urlNextPage = $(opts.next, data).attr('href');

                // update pagination
                $(opts.pagination).replaceWith($(opts.pagination, data));

                remove_loader();
                hide_pagination();

                if (urlNextPage) {
                    reset();
                }
                else {
                    stop_scroll();
                }

                // call the onRenderComplete callback
                opts.onRenderComplete.call(this, items);

                if (onCompleteHandler) {
                    onCompleteHandler.call(this);
                }
            });
        }

        /**
         * Loads items from certain url, triggers
         * onComplete handler when finished
         *
         * @param string   the url to load
         * @param function the callback function
         * @param int      minimal time the loading should take, defaults to $.ias.default.loaderDelay
         * @return void
         */
        function loadItems(url, onCompleteHandler, delay)
        {
            var items = [],
                container,
                startTime = Date.now(),
                diffTime,
                self;

            delay = delay || opts.loaderDelay;

            $.get(url, null, function (data) {
                // walk through the items on the next page
                // and add them to the items array
            	if(data.success){
            		container = data.results;
                    if (0 === container.length) {
                        // incase the element is a root element (body > element),
                        // try to filter it
                        container = $(data).filter(opts.container).eq(0);
                    }

                    if (container) {
                    	weiboUI.paging(container);
//                        container.find(opts.item).each(function () {
//                            items.push(this);
//                        });
                    }

                    if (onCompleteHandler) {
                        self = this;
                        diffTime = Date.now() - startTime;
                        if (diffTime < delay) {
                            setTimeout(function () {
                                onCompleteHandler.call(self, data, items);
                            }, delay - diffTime);
                        } else {
                            onCompleteHandler.call(self, data, items);
                        }
                    }
            	}
                
            }, 'json');
        }

        /**
         * Paginate to a certain page number.
         *
         * - keeps paginating till the pageNum is reached
         *
         * @return void
         */
        function paginateToPage(pageNum)
        {
            var curThreshold = get_scroll_threshold(true);

            if (curThreshold > 0) {
                paginate(curThreshold, function () {
                    stop_scroll();

                    if ((paging.getCurPageNum(curThreshold) + 1) < pageNum) {
                        paginateToPage(pageNum);

                        $('html,body').animate({'scrollTop': curThreshold}, 400, 'swing');
                    }
                    else {
                        $('html,body').animate({'scrollTop': curThreshold}, 1000, 'swing');

                        reset();
                    }
                });
            }
        }

        function get_current_page()
        {
            var curScrOffset = util.getCurrentScrollOffset(opts.scrollContainer);

            return paging.getCurPageNum(curScrOffset);
        }

        /**
         * Return the active loader or creates a new loader
         *
         * @return object loader jquery object
         */
        function get_loader()
        {
            var loader = $('.ias_loader');

            if (loader.size() === 0) {
                loader = $('<div class="ias_loader">' + opts.loader + '</div>');
                loader.hide();
            }
            return loader;
        }

        /**
         * Inserts the loader and does a fadeIn.
         *
         * @return void
         */
        function show_loader()
        {
            var loader = get_loader(),
                el;

            if (opts.customLoaderProc !== false) {
                opts.customLoaderProc(loader);
            } else {
                el = $(opts.container).find(opts.item).last();
                el.after(loader);
                loader.fadeIn();
            }
        }

        /**
         * Removes the loader.
         *
         * return void
         */
        function remove_loader()
        {
            var loader = get_loader();
            loader.remove();
        }

        /**
         * Return the active trigger or creates a new trigger
         *
         * @return object trigger jquery object
         */
        function get_trigger(callback)
        {
            var trigger = $('.ias_trigger');

            if (trigger.size() === 0) {
                trigger = $('<div class="ias_trigger"><a href="javascript:void(0);">' + opts.trigger + '</a></div>');
                trigger.hide();
            }

            $('a', trigger)
                .unbind('click')
                .bind('click', function () { remove_trigger(); callback.call(); return false; })
            ;

            return trigger;
        }

        /**
         * @param function callback of the trigger (get's called onClick)
         */
        function show_trigger(callback)
        {
            var trigger = get_trigger(callback),
                el;

            if (opts.customTriggerProc !== false) {
                opts.customTriggerProc(trigger);
            } else {
                el = $(opts.container).find(opts.item).last();
                el.after(trigger);
                trigger.fadeIn();
            }
        }

        /**
         * Removes the trigger.
         *
         * return void
         */
        function remove_trigger()
        {
            var trigger = get_trigger();

            trigger.remove();
        }
    };

    // plugin defaults
    $.ias.defaults = {
        container: '#container',
        scrollContainer: $(window),
        item: '.item',
        pagination: '#pagination',
        next: '.next',
        noneleft: false,
        loader: '<img src="images/loader.gif"/>',
        loaderDelay: 600,
        triggerPageThreshold: 3,
        trigger: '查看更多',
        thresholdMargin: 0,
        history : true,
        onPageChange: function () {},
        beforePageChange: function () {},
        onLoadItems: function () {},
        onRenderComplete: function () {},
        onScroll: function() {},
        customLoaderProc: false,
        customTriggerProc: false
    };

    // utility module
    $.ias.util = function ()
    {
        // setup
        var wndIsLoaded = false;
        var forceScrollTopIsCompleted = false;
        var self = this;

        /**
         * Initialize
         *
         * @return void
         */
        function init()
        {
            $(window).load(function () {
                wndIsLoaded = true;
            });
        }

        // initialize
        init();

        /**
         * Force browsers to scroll to top.
         *
         * - When you hit back in you browser, it automatically scrolls
         *   back to the last position. There is no way to stop this
         *   in a nice way, so this function does it the hard way.
         *
         * @param function onComplete callback function
         * @return void
         */
        this.forceScrollTop = function (onCompleteHandler)
        {
            $('html,body').scrollTop(0);

            if (!forceScrollTopIsCompleted) {
                if (!wndIsLoaded) {
                    setTimeout(function () {self.forceScrollTop(onCompleteHandler); }, 1);
                } else {
                    onCompleteHandler.call();
                    forceScrollTopIsCompleted = true;
                }
            }
        };

        this.getCurrentScrollOffset = function (container)
        {
            var scrTop,
                wndHeight;

            // the way we calculate if we have to load the next page depends on which container we have
            if (container.get(0) === window) {
                scrTop = container.scrollTop();
            } else {
                scrTop = container.offset().top;
            }

            wndHeight = container.height();

            return scrTop + wndHeight;
        };
    };

    // paging module
    $.ias.paging = function ()
    {
        // setup
        var pagebreaks        = [[0, document.location.toString()]];
        var changePageHandler = function () {};
        var lastPageNum       = 1;
        var util              = new $.ias.util();

        /**
         * Initialize
         *
         * @return void
         */
        function init()
        {
            $(window).scroll(scroll_handler);
        }

        // initialize
        init();

        /**
         * Scroll handler
         *
         * - Triggers changePage event
         *
         * @return void
         */
        function scroll_handler()
        {
            var curScrOffset,
                curPageNum,
                curPagebreak,
                scrOffset,
                urlPage;

            curScrOffset = util.getCurrentScrollOffset($(window));

            curPageNum = getCurPageNum(curScrOffset);
            curPagebreak = getCurPagebreak(curScrOffset);

            if (lastPageNum !== curPageNum) {
                scrOffset = curPagebreak[0];
                urlPage = curPagebreak[1];
                changePageHandler.call({}, curPageNum, scrOffset, urlPage); // @todo fix for window height
            }

            lastPageNum = curPageNum;
        }

        /**
         * Returns current page number based on scroll offset
         *
         * @param int scroll offset
         * @return int current page number
         */
        function getCurPageNum(scrollOffset)
        {
            for (var i = (pagebreaks.length - 1); i > 0; i--) {
                if (scrollOffset > pagebreaks[i][0]) {
                    return i + 1;
                }
            }
            return 1;
        }

        /**
         * Public function for getCurPageNum
         *
         * @param int scrollOffset defaulst to the current
         * @return int current page number
         */
        this.getCurPageNum = function (scrollOffset)
        {
            scrollOffset = scrollOffset || util.getCurrentScrollOffset($(window));

            return getCurPageNum(scrollOffset);
        };

        /**
         * Returns current pagebreak information based on scroll offset
         *
         * @param int scroll offset
         * @return array pagebreak information
         */
        function getCurPagebreak(scrollOffset)
        {
            for (var i = (pagebreaks.length - 1); i >= 0; i--) {
                if (scrollOffset > pagebreaks[i][0]) {
                    return pagebreaks[i];
                }
            }
            return null;
        }

        /**
         * Sets onchangePage event handler
         *
         * @param function event handler
         * @return void
         */
        this.onChangePage = function (fn)
        {
            changePageHandler = fn;
        };

        /**
         * pushes the pages tracker
         *
         * @param int scroll offset for the new page
         * @return void
         */
        this.pushPages = function (scrollOffset, urlNextPage)
        {
            pagebreaks.push([scrollOffset, urlNextPage]);
        };
    };

    // history module
    $.ias.history = function ()
    {
        // setup
        var isPushed = false;
        var isHtml5 = false;

        /**
         * Initialize
         *
         * @return void
         */
        function init()
        {
            isHtml5 = !!(window.history && history.pushState && history.replaceState);
            isHtml5 = false; // html5 functions disabled due to problems in chrome
        }

        // initialize
        init();

        /**
         * Sets page to history
         *
         * @return void;
         */
        this.setPage = function (pageNum, pageUrl)
        {
            this.updateState({page : pageNum}, '', pageUrl);
        };

        /**
         * Checks if we have a page set in the history
         *
         * @return bool returns true when we have a previous page, false otherwise
         */
        this.havePage = function ()
        {
            return false;
        };

        /**
         * Gets the previous page from history
         *
         * @return int page number of previous page
         */
        this.getPage = function ()
        {
            var stateObj;

            if (this.havePage()) {
                stateObj = this.getState();
                return stateObj.page;
            }
            return 1;
        };

        /**
         * Returns current state
         *
         * @return object stateObj
         */
        this.getState = function ()
        {
            var haveState,
                stateObj,
                pageNum;

            if (isHtml5) {
                stateObj = history.state;
                if (stateObj && stateObj.ias) {
                    return stateObj.ias;
                }
            }
            else {
                haveState = (window.location.hash.substring(0, 7) === '#/page/');
                if (haveState) {
                    pageNum = parseInt(window.location.hash.replace('#/page/', ''), 10);
                    return { page : pageNum };
                }
            }

            return false;
        };

        /**
         * Pushes state when not pushed already, otherwise
         * replaces the state.
         *
         * @param obj stateObj
         * @param string title
         * @param string url
         * @return void
         */
        this.updateState = function (stateObj, title, url)
        {
            if (isPushed) {
                this.replaceState(stateObj, title, url);
            }
            else {
                this.pushState(stateObj, title, url);
            }
        };

        /**
         * Pushes state to history.
         *
         * @param obj stateObj
         * @param string title
         * @param string url
         * @return void
         */
        this.pushState = function (stateObj, title, url)
        {
            var hash;

            if (isHtml5) {
                history.pushState({ ias : stateObj }, title, url);
            }
            else {
                hash = (stateObj.page > 0 ? '#/page/' + stateObj.page : '');
                window.location.hash = hash;
            }

            isPushed = true;
        };

        /**
         * Replaces current history state.
         *
         * @param obj stateObj
         * @param string title
         * @param string url
         * @return void
         */
        this.replaceState = function (stateObj, title, url)
        {
            if (isHtml5) {
                history.replaceState({ ias : stateObj }, title, url);
            }
            else {
                this.pushState(stateObj, title, url);
            }
        };
    };
})(jQuery);

/*
Shameless port of a shameless port
@defunkt => @janl => @aq
 
See http://github.com/defunkt/mustache for more info.
*/
 
;(function($) {

/*!
 * mustache.js - Logic-less {{mustache}} templates with JavaScript
 * http://github.com/janl/mustache.js
 */

/*global define: false*/

(function (root, factory) {
  if (typeof exports === "object" && exports) {
    factory(exports); // CommonJS
  } else {
    var mustache = {};
    factory(mustache);
    if (typeof define === "function" && define.amd) {
      define(mustache); // AMD
    } else {
      root.Mustache = mustache; // <script>
    }
  }
}(this, function (mustache) {

  var whiteRe = /\s*/;
  var spaceRe = /\s+/;
  var nonSpaceRe = /\S/;
  var eqRe = /\s*=/;
  var curlyRe = /\s*\}/;
  var tagRe = /#|\^|\/|>|\{|&|=|!/;

  // Workaround for https://issues.apache.org/jira/browse/COUCHDB-577
  // See https://github.com/janl/mustache.js/issues/189
  var RegExp_test = RegExp.prototype.test;
  function testRegExp(re, string) {
    return RegExp_test.call(re, string);
  }

  function isWhitespace(string) {
    return !testRegExp(nonSpaceRe, string);
  }

  var Object_toString = Object.prototype.toString;
  var isArray = Array.isArray || function (object) {
    return Object_toString.call(object) === '[object Array]';
  };

  function isFunction(object) {
    return typeof object === 'function';
  }

  function escapeRegExp(string) {
    return string.replace(/[\-\[\]{}()*+?.,\\\^$|#\s]/g, "\\$&");
  }

  var entityMap = {
    "&": "&amp;",
    "<": "&lt;",
    ">": "&gt;",
    '"': '&quot;',
    "'": '&#39;',
    "/": '&#x2F;'
  };

  function escapeHtml(string) {
    return String(string).replace(/[&<>"'\/]/g, function (s) {
      return entityMap[s];
    });
  }

  function Scanner(string) {
    this.string = string;
    this.tail = string;
    this.pos = 0;
  }

  /**
   * Returns `true` if the tail is empty (end of string).
   */
  Scanner.prototype.eos = function () {
    return this.tail === "";
  };

  /**
   * Tries to match the given regular expression at the current position.
   * Returns the matched text if it can match, the empty string otherwise.
   */
  Scanner.prototype.scan = function (re) {
    var match = this.tail.match(re);

    if (match && match.index === 0) {
      var string = match[0];
      this.tail = this.tail.substring(string.length);
      this.pos += string.length;
      return string;
    }

    return "";
  };

  /**
   * Skips all text until the given regular expression can be matched. Returns
   * the skipped string, which is the entire tail if no match can be made.
   */
  Scanner.prototype.scanUntil = function (re) {
    var index = this.tail.search(re), match;

    switch (index) {
    case -1:
      match = this.tail;
      this.tail = "";
      break;
    case 0:
      match = "";
      break;
    default:
      match = this.tail.substring(0, index);
      this.tail = this.tail.substring(index);
    }

    this.pos += match.length;

    return match;
  };

  function Context(view, parent) {
    this.view = view == null ? {} : view;
    this.parent = parent;
    this._cache = { '.': this.view };
  }

  Context.make = function (view) {
    return (view instanceof Context) ? view : new Context(view);
  };

  Context.prototype.push = function (view) {
    return new Context(view, this);
  };

  Context.prototype.lookup = function (name) {
    var value;
    if (name in this._cache) {
      value = this._cache[name];
    } else {
      var context = this;

      while (context) {
        if (name.indexOf('.') > 0) {
          value = context.view;

          var names = name.split('.'), i = 0;
          while (value != null && i < names.length) {
            value = value[names[i++]];
          }
        } else {
          value = context.view[name];
        }

        if (value != null) break;

        context = context.parent;
      }

      this._cache[name] = value;
    }

    if (isFunction(value)) {
      value = value.call(this.view);
    }

    return value;
  };

  function Writer() {
    this.clearCache();
  }

  Writer.prototype.clearCache = function () {
    this._cache = {};
    this._partialCache = {};
  };

  Writer.prototype.compile = function (template, tags) {
    var fn = this._cache[template];

    if (!fn) {
      var tokens = mustache.parse(template, tags);
      fn = this._cache[template] = this.compileTokens(tokens, template);
    }

    return fn;
  };

  Writer.prototype.compilePartial = function (name, template, tags) {
    var fn = this.compile(template, tags);
    this._partialCache[name] = fn;
    return fn;
  };

  Writer.prototype.getPartial = function (name) {
    if (!(name in this._partialCache) && this._loadPartial) {
      this.compilePartial(name, this._loadPartial(name));
    }

    return this._partialCache[name];
  };

  Writer.prototype.compileTokens = function (tokens, template) {
    var self = this;
    return function (view, partials) {
      if (partials) {
        if (isFunction(partials)) {
          self._loadPartial = partials;
        } else {
          for (var name in partials) {
            self.compilePartial(name, partials[name]);
          }
        }
      }

      return renderTokens(tokens, self, Context.make(view), template);
    };
  };

  Writer.prototype.render = function (template, view, partials) {
    return this.compile(template)(view, partials);
  };

  /**
   * Low-level function that renders the given `tokens` using the given `writer`
   * and `context`. The `template` string is only needed for templates that use
   * higher-order sections to extract the portion of the original template that
   * was contained in that section.
   */
  function renderTokens(tokens, writer, context, template) {
    var buffer = '';

    // This function is used to render an artbitrary template
    // in the current context by higher-order functions.
    function subRender(template) {
      return writer.render(template, context);
    }

    var token, tokenValue, value;
    for (var i = 0, len = tokens.length; i < len; ++i) {
      token = tokens[i];
      tokenValue = token[1];

      switch (token[0]) {
      case '#':
        value = context.lookup(tokenValue);

        if (typeof value === 'object' || typeof value === 'string') {
          if (isArray(value)) {
            for (var j = 0, jlen = value.length; j < jlen; ++j) {
              buffer += renderTokens(token[4], writer, context.push(value[j]), template);
            }
          } else if (value) {
            buffer += renderTokens(token[4], writer, context.push(value), template);
          }
        } else if (isFunction(value)) {
          var text = template == null ? null : template.slice(token[3], token[5]);
          value = value.call(context.view, text, subRender);
          if (value != null) buffer += value;
        } else if (value) {
          buffer += renderTokens(token[4], writer, context, template);
        }

        break;
      case '^':
        value = context.lookup(tokenValue);

        // Use JavaScript's definition of falsy. Include empty arrays.
        // See https://github.com/janl/mustache.js/issues/186
        if (!value || (isArray(value) && value.length === 0)) {
          buffer += renderTokens(token[4], writer, context, template);
        }

        break;
      case '>':
        value = writer.getPartial(tokenValue);
        if (isFunction(value)) buffer += value(context);
        break;
      case '&':
        value = context.lookup(tokenValue);
        if (value != null) buffer += value;
        break;
      case 'name':
        value = context.lookup(tokenValue);
        if (value != null) buffer += mustache.escape(value);
        break;
      case 'text':
        buffer += tokenValue;
        break;
      }
    }

    return buffer;
  }

  /**
   * Forms the given array of `tokens` into a nested tree structure where
   * tokens that represent a section have two additional items: 1) an array of
   * all tokens that appear in that section and 2) the index in the original
   * template that represents the end of that section.
   */
  function nestTokens(tokens) {
    var tree = [];
    var collector = tree;
    var sections = [];

    var token;
    for (var i = 0, len = tokens.length; i < len; ++i) {
      token = tokens[i];
      switch (token[0]) {
      case '#':
      case '^':
        sections.push(token);
        collector.push(token);
        collector = token[4] = [];
        break;
      case '/':
        var section = sections.pop();
        section[5] = token[2];
        collector = sections.length > 0 ? sections[sections.length - 1][4] : tree;
        break;
      default:
        collector.push(token);
      }
    }

    return tree;
  }

  /**
   * Combines the values of consecutive text tokens in the given `tokens` array
   * to a single token.
   */
  function squashTokens(tokens) {
    var squashedTokens = [];

    var token, lastToken;
    for (var i = 0, len = tokens.length; i < len; ++i) {
      token = tokens[i];
      if (token) {
        if (token[0] === 'text' && lastToken && lastToken[0] === 'text') {
          lastToken[1] += token[1];
          lastToken[3] = token[3];
        } else {
          lastToken = token;
          squashedTokens.push(token);
        }
      }
    }

    return squashedTokens;
  }

  function escapeTags(tags) {
    return [
      new RegExp(escapeRegExp(tags[0]) + "\\s*"),
      new RegExp("\\s*" + escapeRegExp(tags[1]))
    ];
  }

  /**
   * Breaks up the given `template` string into a tree of token objects. If
   * `tags` is given here it must be an array with two string values: the
   * opening and closing tags used in the template (e.g. ["<%", "%>"]). Of
   * course, the default is to use mustaches (i.e. Mustache.tags).
   */
  function parseTemplate(template, tags) {
    template = template || '';
    tags = tags || mustache.tags;

    if (typeof tags === 'string') tags = tags.split(spaceRe);
    if (tags.length !== 2) throw new Error('Invalid tags: ' + tags.join(', '));

    var tagRes = escapeTags(tags);
    var scanner = new Scanner(template);

    var sections = [];     // Stack to hold section tokens
    var tokens = [];       // Buffer to hold the tokens
    var spaces = [];       // Indices of whitespace tokens on the current line
    var hasTag = false;    // Is there a {{tag}} on the current line?
    var nonSpace = false;  // Is there a non-space char on the current line?

    // Strips all whitespace tokens array for the current line
    // if there was a {{#tag}} on it and otherwise only space.
    function stripSpace() {
      if (hasTag && !nonSpace) {
        while (spaces.length) {
          delete tokens[spaces.pop()];
        }
      } else {
        spaces = [];
      }

      hasTag = false;
      nonSpace = false;
    }

    var start, type, value, chr, token, openSection;
    while (!scanner.eos()) {
      start = scanner.pos;

      // Match any text between tags.
      value = scanner.scanUntil(tagRes[0]);
      if (value) {
        for (var i = 0, len = value.length; i < len; ++i) {
          chr = value.charAt(i);

          if (isWhitespace(chr)) {
            spaces.push(tokens.length);
          } else {
            nonSpace = true;
          }

          tokens.push(['text', chr, start, start + 1]);
          start += 1;

          // Check for whitespace on the current line.
          if (chr == '\n') stripSpace();
        }
      }

      // Match the opening tag.
      if (!scanner.scan(tagRes[0])) break;
      hasTag = true;

      // Get the tag type.
      type = scanner.scan(tagRe) || 'name';
      scanner.scan(whiteRe);

      // Get the tag value.
      if (type === '=') {
        value = scanner.scanUntil(eqRe);
        scanner.scan(eqRe);
        scanner.scanUntil(tagRes[1]);
      } else if (type === '{') {
        value = scanner.scanUntil(new RegExp('\\s*' + escapeRegExp('}' + tags[1])));
        scanner.scan(curlyRe);
        scanner.scanUntil(tagRes[1]);
        type = '&';
      } else {
        value = scanner.scanUntil(tagRes[1]);
      }

      // Match the closing tag.
      if (!scanner.scan(tagRes[1])) throw new Error('Unclosed tag at ' + scanner.pos);

      token = [type, value, start, scanner.pos];
      tokens.push(token);

      if (type === '#' || type === '^') {
        sections.push(token);
      } else if (type === '/') {
        // Check section nesting.
        openSection = sections.pop();
        if (!openSection) {
          throw new Error('Unopened section "' + value + '" at ' + start);
        }
        if (openSection[1] !== value) {
          throw new Error('Unclosed section "' + openSection[1] + '" at ' + start);
        }
      } else if (type === 'name' || type === '{' || type === '&') {
        nonSpace = true;
      } else if (type === '=') {
        // Set the tags for the next time around.
        tags = value.split(spaceRe);
        if (tags.length !== 2) {
          throw new Error('Invalid tags at ' + start + ': ' + tags.join(', '));
        }
        tagRes = escapeTags(tags);
      }
    }

    // Make sure there are no open sections when we're done.
    openSection = sections.pop();
    if (openSection) {
      throw new Error('Unclosed section "' + openSection[1] + '" at ' + scanner.pos);
    }

    return nestTokens(squashTokens(tokens));
  }

  mustache.name = "mustache.js";
  mustache.version = "0.7.3";
  mustache.tags = ["{{", "}}"];

  mustache.Scanner = Scanner;
  mustache.Context = Context;
  mustache.Writer = Writer;

  mustache.parse = parseTemplate;

  // Export the escaping function so that the user may override it.
  // See https://github.com/janl/mustache.js/issues/244
  mustache.escape = escapeHtml;

  // All Mustache.* functions use this writer.
  var defaultWriter = new Writer();

  /**
   * Clears all cached templates and partials in the default writer.
   */
  mustache.clearCache = function () {
    return defaultWriter.clearCache();
  };

  /**
   * Compiles the given `template` to a reusable function using the default
   * writer.
   */
  mustache.compile = function (template, tags) {
    return defaultWriter.compile(template, tags);
  };

  /**
   * Compiles the partial with the given `name` and `template` to a reusable
   * function using the default writer.
   */
  mustache.compilePartial = function (name, template, tags) {
    return defaultWriter.compilePartial(name, template, tags);
  };

  /**
   * Compiles the given array of tokens (the output of a parse) to a reusable
   * function using the default writer.
   */
  mustache.compileTokens = function (tokens, template) {
    return defaultWriter.compileTokens(tokens, template);
  };

  /**
   * Renders the `template` with the given `view` and `partials` using the
   * default writer.
   */
  mustache.render = function (template, view, partials) {
    return defaultWriter.render(template, view, partials);
  };

  // This is here for backwards compatibility with 0.4.x.
  mustache.to_html = function (template, view, partials, send) {
    var result = mustache.render(template, view, partials);

    if (isFunction(send)) {
      send(result);
    } else {
      return result;
    }
  };

}));
  $.mustache = function (template, view, partials) {
    return Mustache.render(template, view, partials);
  };

  $.fn.mustache = function (view, partials) {
    return $(this).map(function (i, elm) {
      var template = $.trim($(elm).html());
      var output = $.mustache(template, view, partials);
      return $(output).get();
    });
  };

})(jQuery);

/**
 * jQuery工具类plugin。
 * 例如为 selector results 提供 isEmpty() 方法。
 * 依赖:mustache 插件.
 * 
 * @author Yang Bo
 */

(function($){
	function log(msg){
		console.log(msg);
	}
	
	/**
	 * 本方法强制认为str是本地时间，时区为+0800，且str不带时区后缀。
	 * 
	 * 转换为通用格式：Date.parse('1970/01/01 00:00:00 +0800'); 
	 */
	function strToDate(str){
		if (str instanceof Date){
			return str;
		}
		str = str.replace('T', ' ');
		str = str.replace(/-/g, '/');
		str += " +0800";
		var date = new Date(Date.parse(str));

		return date;
	}
	function padding(num){
		if(num>=10){
			return num;
		}else{
			return '0'+num;
		}
	}
	
	$.strToDate = strToDate;
	
	/**
	 * 格式化输出短时间字符串。
	 * 
	 * 根据与当前时间的差距，用不同格式打印时间。
	 * 参数：
	 * 	time 要格式化的时间
	 * 	reference 参考时间，可以不提供，不提供就用当前时间
	 * 
	 * 输出：字符串，可能的格式
	 * 1) 3秒前
	 * 2) 59分钟前
	 * 3) 今天 15:27
	 * 4) 05-07 07:06
	 * 5) 2013-02-01 12:35
	 */
	$.shortTime = function(time, reference){
		var cur = reference || new Date();
		var dateObj = strToDate(time);
		
		var interval = Math.floor((cur - dateObj.getTime())/1000);
		var month = 1+dateObj.getMonth();
		var day = dateObj.getDate();
		var hours = dateObj.getHours();
		var minutes = dateObj.getMinutes();
		month = padding(month);
		day = padding(day);
		hours = padding(hours);
		minutes = padding(minutes);
		
		// 秒
		if (interval>=0 && interval <60){
			return interval+'秒前';
		}
		// 分钟
		if (interval >= 60 && interval < 3600){
			return Math.floor(interval/60) + '分钟前';
		}
		// 今天
		if (cur.getYear()==dateObj.getYear() &&
			cur.getMonth()==dateObj.getMonth() &&
			cur.getDate()==dateObj.getDate()){
			return '今天 '+ hours +':'+ minutes;
		}
		// 同年
		if (cur.getYear() == dateObj.getYear()){
			return month+'-'+day+' '+hours+':'+minutes;
		}
		// 不同年
		var year = dateObj.getFullYear();
		return year+'-'+month+'-'+day+' '+hours+':'+minutes;
	};
	
	$.fn.isEmpty = function(){
		return this.length == 0;
	};
	
	$.fn.msgbox = function(msg, options){
		var me = this;
		$.msgbox(msg, $.extend({anchor: me}, options));
	};
	
	/**
	 * 选项:{
	 *  anchor: positioning element
	 * 	success: true|false 决定用成功提示还是失败提示样式，
	 *  timeout: 自动消失时间,
	 *  pos: 'center' 在屏幕中间显示
	 * }
	 */
	$.msgbox = function(msg, options){
		var settings = $.extend({
			success: true
			,timeout: 3000
			//,pos: 'center'
		}, options);
		var anchor = settings.anchor;
		var msgbox = $.mustache(
				'<div class="msgbox">'+
					'<div class="box_content"><span class="{{type_class}}">{{content}}</span></div>'+
				'</div>',{
			content: msg,
			type_class: settings.success?'successtip':'failtip'
		});
		msgbox = $(msgbox);
		$('body').append(msgbox);
		var left = 0;
		var top = 0;
		if (!anchor || settings.pos == 'center'){
			left = $(window).width()/2 + $(document).scrollLeft() - msgbox.outerWidth()/2.3;
			top = $(window).height()/2 + $(document).scrollTop() - msgbox.outerHeight()*1.2;
		}else{
			var anchorPos = anchor.offset();
			left = anchorPos.left - msgbox.outerWidth()/2.3;
			top = anchorPos.top - msgbox.outerHeight()*1.2;
		}
		msgbox.css('left', left);
		msgbox.css('top', top);
		msgbox.show();
		// 自动消失
		setTimeout(function(){
			msgbox.hide();
		}, settings.timeout);
	};
	// alias
	$.msgBox = $.msgbox;
	
	$.isBlankStr = function(str){
		var removed = str.replace(/ +/, "");
		return removed.length == 0;
	};
	
}(jQuery));
var searchKeys;
$(document).ready(function() {
	bindSearchAction();
	//点击放大镜搜索
	$(".magnifier a").click(function(){
	   var keyWord=$("#head-search").val();
	   var trimWord=$.trim(keyWord);
	    // if(trimWord){
		//   doSearch(trimWord);
	    //  }
	})
});

var barSearchFlag;
//绑定搜索动作
function bindSearchAction(){
	//head-search  old quick-search
	$('#head-search').live("keyup",function(e){
		$this = $(this);
		barSearchFlag = $this.val();
		if($.trim($this.val()) == ""){
			//search_result  old search-result
			$('#search_result').hide();
		} else {
			setTimeout(barSearch($this.val()),1000);
		}
	})

	$("#head-search").live("keypress",function(e){
		//var e = e || window.event;
		if(e.keyCode == 13){ 
			var keys = $(this).val();
			e.preventDefault();
			if(keys != ""){
				doSearch(keys);
			}
		}
	})
}
function doSearch(keys){
	window.location.href='http://www.davcai.com/search_weibo?keys=' + encodeURIComponent(keys);
}

function barSearch(searchKeys){
	 var weiboHeadLi = '<li class="no-menu"><h3>讨论</h3></li>';
	 var weiboSearchLi='<li data-value="" class=""> <a href="http://www.davcai.com/search_weibo?keys={1}">搜索&nbsp;&nbsp;<em>“{0}”</em>&nbsp;&nbsp;相关讨论 </a></li>';
	 var userHeadLi = '<li class="no-menu"><h3>用户</h3></li>';
	 var userSearchLi ='<li class="dropdown-menu-user"><a href="http://www.davcai.com/{2}/profile" class="dropdown-menu-user-logo"><img style="width:36px;height:36px;" src="{0}" /></a><a href="http://www.davcai.com/{2}/profile" class="dropdown-menu-user-name"><em>{1}</em></a></li>';
	 var userSearchLiEnd='<li data-value="" class=""><a href="http://www.davcai.com/search_user?keys={1}">搜索&nbsp;&nbsp;<em>“{0}”</em>&nbsp;&nbsp;相关用户</a></li>';
    $('#search_result').show();
	$.getJSON("http://www.davcai.com/ajax_search_user?keys=" + encodeURIComponent(searchKeys) + '&page=1&size=5&callback=?', function(result) {
		if (result.users.success && barSearchFlag == searchKeys) {
			if(result.users.results.length > 0){
				var users = result.users.results;
				var userLi = "";
				$('#search_result').html("");
				var lis= weiboHeadLi + $.format(weiboSearchLi,searchKeys,encodeURIComponent(searchKeys)) + userHeadLi;
				$('#search_result').append(lis);
				for ( var i = 0; i < users.length; i++) {
					userLi += $.format(userSearchLi,users[i].headImageURL,users[i].nickName,users[i].weiboUserId);
				}
				$('#search_result').append(userLi);
			} else {
				$('#search_result').html("");
				var lis= weiboHeadLi + $.format(weiboSearchLi,searchKeys,encodeURIComponent(searchKeys)) + userHeadLi;
				$('#search_result').append(lis);
			}
			$('#search_result').append($.format(userSearchLiEnd,searchKeys,encodeURIComponent(searchKeys)));
		}
	}, 'json').error(function(e) {
		$.showMessage( "操作失败，请刷新页面重试！");
	});
}
/**
 * 前端彩种的js对象
 * @returns
 */
function lotteryId() {
}
function lotteryId(id, name) {
	this.id = id;
	this.name = name;
}
lotteryId.prototype.id = "";
lotteryId.prototype.name = "";

var lotteryName = {
	JCZQ : "JCZQ",
	JCLQ : "JCLQ",
	CTZC : "CTZC",
	SSQ :  "SSQ",
	JX11 : "JX11",
	BJDC : "BJDC"
};

function lottery() {
}
lottery.prototype.list = function() {
	var lotteryList = [];
	lotteryList.push(new lotteryId("JCZQ", "竞足"));
	lotteryList.push(new lotteryId("JCLQ", "竞篮"));
	lotteryList.push(new lotteryId("CTZC", "足彩"));
	lotteryList.push(new lotteryId("SSQ", "双色球"));
	lotteryList.push(new lotteryId("BJDC", "北单"));
	lotteryList.push(new lotteryId("JX11", "11选五"));
	return lotteryList;
};
lottery.prototype.getId = function(name) {
	var id = null;
	var arr = this.list();
	for(var i=0; i<arr.length; i++) {
		var lott = arr[i];
		if(name == lott.name) {
			id = lott.id;
			break;
		}
	}
	return id;
};
lottery.prototype.getName = function(id) {
	var name = '未知';
	var arr = this.list();
	for(var i=0; i<arr.length; i++) {
		var lott = arr[i];
		if(id == lott.id) {
			name = lott.name;
			break;
		}
	}
	return name;
};

lottery.prototype.isDigital = function(id) {
	if(lotteryName.SSQ == id || lotteryName.JX11 == id) {
		return true;
	} else {
		return false;
	}
};

lottery.prototype.isZC = function(lotteryType) {
	return lotteryName.JCZQ == lotteryType ||
	lotteryName.CTZC == lotteryType ||
	lotteryName.BJDC == lotteryType;
};

lottery.prototype.isLC = function(lotteryType) {
	return lotteryName.JCLQ == lotteryType;
};

lottery.prototype.isBJDC = function(lotteryType) {
	return lotteryName.BJDC == lotteryType;
};
/*
  Implement Github like autocomplete mentions
  http://ichord.github.com/At.js

  Copyright (c) 2013 chord.luo@gmail.com
  Licensed under the MIT license.
*/


/*
本插件操作 textarea 或者 input 内的插入符
只实现了获得插入符在文本框中的位置，我设置
插入符的位置.
*/


(function() {
  (function(factory) {
    if (typeof define === 'function' && define.amd) {
      return define(['jquery'], factory);
    } else {
      return factory(window.jQuery);
    }
  })(function($) {
    "use strict";
    var EditableCaret, InputCaret, Mirror, Utils, methods, oDocument, oFrame, oWindow, pluginName;
    pluginName = 'caret';
    EditableCaret = (function() {
      function EditableCaret($inputor) {
        this.$inputor = $inputor;
        this.domInputor = this.$inputor[0];
      }

      EditableCaret.prototype.setPos = function(pos) {
        return this.domInputor;
      };

      EditableCaret.prototype.getIEPosition = function() {
        return $.noop();
      };

      EditableCaret.prototype.getPosition = function() {
        return $.noop();
      };

      EditableCaret.prototype.getOldIEPos = function() {
        var preCaretTextRange, textRange;
        textRange = oDocument.selection.createRange();
        preCaretTextRange = oDocument.body.createTextRange();
        preCaretTextRange.moveToElementText(this.domInputor);
        preCaretTextRange.setEndPoint("EndToEnd", textRange);
        return preCaretTextRange.text.length;
      };

      EditableCaret.prototype.getPos = function() {
        var clonedRange, pos, range;
        if (range = this.range()) {
          clonedRange = range.cloneRange();
          clonedRange.selectNodeContents(this.domInputor);
          clonedRange.setEnd(range.endContainer, range.endOffset);
          pos = clonedRange.toString().length;
          clonedRange.detach();
          return pos;
        } else if (oDocument.selection) {
          return this.getOldIEPos();
        }
      };

      EditableCaret.prototype.getOldIEOffset = function() {
        var range, rect;
        range = oDocument.selection.createRange().duplicate();
        range.moveStart("character", -1);
        rect = range.getBoundingClientRect();
        return {
          height: rect.bottom - rect.top,
          left: rect.left,
          top: rect.top
        };
      };

      EditableCaret.prototype.getOffset = function(pos) {
        var clonedRange, offset, range, rect;
        if (oWindow.getSelection && (range = this.range())) {
          if (range.endOffset - 1 < 0) {
            return null;
          }
          clonedRange = range.cloneRange();
          clonedRange.setStart(range.endContainer, range.endOffset - 1);
          clonedRange.setEnd(range.endContainer, range.endOffset);
          rect = clonedRange.getBoundingClientRect();
          offset = {
            height: rect.height,
            left: rect.left + rect.width,
            top: rect.top
          };
          clonedRange.detach();
        } else if (oDocument.selection) {
          offset = this.getOldIEOffset();
        }
        if (offset && !oFrame) {
          offset.top += $(oWindow).scrollTop();
          offset.left += $(oWindow).scrollLeft();
        }
        return offset;
      };

      EditableCaret.prototype.range = function() {
        var sel;
        if (!oWindow.getSelection) {
          return;
        }
        sel = oWindow.getSelection();
        if (sel.rangeCount > 0) {
          return sel.getRangeAt(0);
        } else {
          return null;
        }
      };

      return EditableCaret;

    })();
    InputCaret = (function() {
      function InputCaret($inputor) {
        this.$inputor = $inputor;
        this.domInputor = this.$inputor[0];
      }

      InputCaret.prototype.getIEPos = function() {
        var endRange, inputor, len, normalizedValue, pos, range, textInputRange;
        inputor = this.domInputor;
        range = oDocument.selection.createRange();
        pos = 0;
        if (range && range.parentElement() === inputor) {
          normalizedValue = inputor.value.replace(/\r\n/g, "\n");
          len = normalizedValue.length;
          textInputRange = inputor.createTextRange();
          textInputRange.moveToBookmark(range.getBookmark());
          endRange = inputor.createTextRange();
          endRange.collapse(false);
          if (textInputRange.compareEndPoints("StartToEnd", endRange) > -1) {
            pos = len;
          } else {
            pos = -textInputRange.moveStart("character", -len);
          }
        }
        return pos;
      };

      InputCaret.prototype.getPos = function() {
        if (oDocument.selection) {
          return this.getIEPos();
        } else {
          return this.domInputor.selectionStart;
        }
      };

      InputCaret.prototype.setPos = function(pos) {
        var inputor, range;
        inputor = this.domInputor;
        if (oDocument.selection) {
          range = inputor.createTextRange();
          range.move("character", pos);
          range.select();
        } else if (inputor.setSelectionRange) {
          inputor.setSelectionRange(pos, pos);
        }
        return inputor;
      };

      InputCaret.prototype.getIEOffset = function(pos) {
        var h, textRange, x, y;
        textRange = this.domInputor.createTextRange();
        pos || (pos = this.getPos());
        textRange.move('character', pos);
        x = textRange.boundingLeft;
        y = textRange.boundingTop;
        h = textRange.boundingHeight;
        return {
          left: x,
          top: y,
          height: h
        };
      };

      InputCaret.prototype.getOffset = function(pos) {
        var $inputor, offset, position;
        $inputor = this.$inputor;
        if (oDocument.selection) {
          offset = this.getIEOffset(pos);
          offset.top += $(oWindow).scrollTop() + $inputor.scrollTop();
          offset.left += $(oWindow).scrollLeft() + $inputor.scrollLeft();
          return offset;
        } else {
          offset = $inputor.offset();
          position = this.getPosition(pos);
          return offset = {
            left: offset.left + position.left - $inputor.scrollLeft(),
            top: offset.top + position.top - $inputor.scrollTop(),
            height: position.height
          };
        }
      };

      InputCaret.prototype.getPosition = function(pos) {
        var $inputor, at_rect, format, html, mirror, start_range;
        $inputor = this.$inputor;
        format = function(value) {
          return value.replace(/</g, '&lt').replace(/>/g, '&gt').replace(/`/g, '&#96').replace(/"/g, '&quot').replace(/\r\n|\r|\n/g, "<br />");
        };
        if (pos === void 0) {
          pos = this.getPos();
        }
        start_range = $inputor.val().slice(0, pos);
        html = "<span>" + format(start_range) + "</span>";
        html += "<span id='caret'>|</span>";
        mirror = new Mirror($inputor);
        return at_rect = mirror.create(html).rect();
      };

      InputCaret.prototype.getIEPosition = function(pos) {
        var h, inputorOffset, offset, x, y;
        offset = this.getIEOffset(pos);
        inputorOffset = this.$inputor.offset();
        x = offset.left - inputorOffset.left;
        y = offset.top - inputorOffset.top;
        h = offset.height;
        return {
          left: x,
          top: y,
          height: h
        };
      };

      return InputCaret;

    })();
    Mirror = (function() {
      Mirror.prototype.css_attr = ["overflowY", "height", "width", "paddingTop", "paddingLeft", "paddingRight", "paddingBottom", "marginTop", "marginLeft", "marginRight", "marginBottom", "fontFamily", "borderStyle", "borderWidth", "wordWrap", "fontSize", "lineHeight", "overflowX", "text-align"];

      function Mirror($inputor) {
        this.$inputor = $inputor;
      }

      Mirror.prototype.mirrorCss = function() {
        var css,
          _this = this;
        css = {
          position: 'absolute',
          left: -9999,
          top: 0,
          zIndex: -20000,
          'white-space': 'pre-wrap'
        };
        $.each(this.css_attr, function(i, p) {
          return css[p] = _this.$inputor.css(p);
        });
        return css;
      };

      Mirror.prototype.create = function(html) {
        this.$mirror = $('<div></div>');
        this.$mirror.css(this.mirrorCss());
        this.$mirror.html(html);
        this.$inputor.after(this.$mirror);
        return this;
      };

      Mirror.prototype.rect = function() {
        var $flag, pos, rect;
        $flag = this.$mirror.find("#caret");
        pos = $flag.position();
        rect = {
          left: pos.left,
          top: pos.top,
          height: $flag.height()
        };
        this.$mirror.remove();
        return rect;
      };

      return Mirror;

    })();
    Utils = {
      contentEditable: function($inputor) {
        return !!($inputor[0].contentEditable && $inputor[0].contentEditable === 'true');
      }
    };
    methods = {
      pos: function(pos) {
        if (pos) {
          return this.setPos(pos);
        } else {
          return this.getPos();
        }
      },
      position: function(pos) {
        if (oDocument.selection) {
          return this.getIEPosition(pos);
        } else {
          return this.getPosition(pos);
        }
      },
      offset: function(pos) {
        var iOffset, offset;
        offset = this.getOffset(pos);
        if (oFrame) {
          iOffset = $(oFrame).offset();
          offset.top += iOffset.top;
          offset.left += iOffset.left;
        }
        return offset;
      }
    };
    oDocument = null;
    oWindow = null;
    oFrame = null;
    $.fn.caret = function(method) {
      var caret;
      oDocument = this[0].ownerDocument;
      oWindow = oDocument.defaultView || oDocument.parentWindow;
      oFrame = oWindow.frameElement;
      caret = Utils.contentEditable(this) ? new EditableCaret(this) : new InputCaret(this);
      if (methods[method]) {
        return methods[method].apply(caret, Array.prototype.slice.call(arguments, 1));
      } else {
        return $.error("Method " + method + " does not exist on jQuery.caret");
      }
    };
    $.fn.caret.EditableCaret = EditableCaret;
    $.fn.caret.InputCaret = InputCaret;
    $.fn.caret.Utils = Utils;
    return $.fn.caret.apis = methods;
  });

}).call(this);


/*
  Implement Github like autocomplete mentions
  http://ichord.github.com/At.js

  Copyright (c) 2013 chord.luo@gmail.com
  Licensed under the MIT license.
*/


(function() {
  var __slice = [].slice;

  (function(factory) {
    if (typeof define === 'function' && define.amd) {
      return define(['jquery'], factory);
    } else {
      return factory(window.jQuery);
    }
  })(function($) {
    var $CONTAINER, Api, App, Atwho, Controller, DEFAULT_CALLBACKS, KEY_CODE, Model, View;
    App = (function() {

      function App(inputor) {
        this.current_flag = null;
        this.controllers = {};
        this.alias_maps = {};
        this.$inputor = $(inputor);
        this.listen();
      }

      App.prototype.controller = function(at) {
        return this.controllers[this.alias_maps[at] || at || this.current_flag];
      };

      App.prototype.set_context_for = function(at) {
        this.current_flag = at;
        return this;
      };

      App.prototype.reg = function(flag, setting) {
        var controller, _base;
        controller = (_base = this.controllers)[flag] || (_base[flag] = new Controller(this, flag));
        if (setting.alias) {
          this.alias_maps[setting.alias] = flag;
        }
        controller.init(setting);
        return this;
      };

      App.prototype.listen = function() {
        var _this = this;
        return this.$inputor.on('keyup.atwho', function(e) {
          return _this.on_keyup(e);
        }).on('keydown.atwho', function(e) {
          return _this.on_keydown(e);
        }).on('scroll.atwho', function(e) {
          var _ref;
          return (_ref = _this.controller()) != null ? _ref.view.hide() : void 0;
        }).on('blur.atwho', function(e) {
          var c;
          if (c = _this.controller()) {
            return c.view.hide(c.get_opt("display_timeout"));
          }
        });
      };

      App.prototype.dispatch = function() {
        var _this = this;
        return $.map(this.controllers, function(c) {
          if (c.look_up()) {
            return _this.set_context_for(c.at);
          }
        });
      };

      App.prototype.on_keyup = function(e) {
        var _ref;
        switch (e.keyCode) {
          case KEY_CODE.ESC:
            e.preventDefault();
            if ((_ref = this.controller()) != null) {
              _ref.view.hide();
            }
            break;
          case KEY_CODE.DOWN:
          case KEY_CODE.UP:
            $.noop();
            break;
          default:
            this.dispatch();
        }
      };

      App.prototype.on_keydown = function(e) {
        var view, _ref;
        view = (_ref = this.controller()) != null ? _ref.view : void 0;
        if (!(view && view.visible())) {
          return;
        }
        switch (e.keyCode) {
          case KEY_CODE.ESC:
            e.preventDefault();
            view.hide();
            break;
          case KEY_CODE.UP:
            e.preventDefault();
            view.prev();
            break;
          case KEY_CODE.DOWN:
            e.preventDefault();
            view.next();
            break;
          case KEY_CODE.TAB:
          case KEY_CODE.ENTER:
            if (!view.visible()) {
              return;
            }
            e.preventDefault();
            view.choose();
            break;
          default:
            $.noop();
        }
      };

      return App;

    })();
    Controller = (function() {
      var uuid, _uuid;

      _uuid = 0;

      uuid = function() {
        return _uuid += 1;
      };

      function Controller(app, at) {
        this.app = app;
        this.at = at;
        this.$inputor = this.app.$inputor;
        this.oDocument = this.$inputor[0].ownerDocument;
        this.oWindow = this.oDocument.defaultView || this.oDocument.parentWindow;
        this.id = this.$inputor[0].id || uuid();
        this.setting = null;
        this.query = null;
        this.pos = 0;
        this.cur_rect = null;
        this.range = null;
        $CONTAINER.append(this.$el = $("<div id='atwho-ground-" + this.id + "'></div>"));
        this.model = new Model(this);
        this.view = new View(this);
      }

      Controller.prototype.init = function(setting) {
        this.setting = $.extend({}, this.setting || $.fn.atwho["default"], setting);
        this.view.init();
        return this.model.reload(this.setting.data);
      };

      Controller.prototype.call_default = function() {
        var args, func_name;
        func_name = arguments[0], args = 2 <= arguments.length ? __slice.call(arguments, 1) : [];
        try {
          return DEFAULT_CALLBACKS[func_name].apply(this, args);
        } catch (error) {
          return $.error("" + error + " Or maybe At.js doesn't have function " + func_name);
        }
      };

      Controller.prototype.trigger = function(name, data) {
        var alias, event_name;
        data.push(this);
        alias = this.get_opt('alias');
        event_name = alias ? "" + name + "-" + alias + ".atwho" : "" + name + ".atwho";
        return this.$inputor.trigger(event_name, data);
      };

      Controller.prototype.callbacks = function(func_name) {
        return this.get_opt("callbacks")[func_name] || DEFAULT_CALLBACKS[func_name];
      };

      Controller.prototype.get_opt = function(at, default_value) {
        try {
          return this.setting[at];
        } catch (e) {
          return null;
        }
      };

      Controller.prototype.content = function() {
        if (this.$inputor.is('textarea, input')) {
          return this.$inputor.val();
        } else {
          return this.$inputor.text();
        }
      };

      Controller.prototype.catch_query = function() {
        var caret_pos, content, end, query, start, subtext;
        content = this.content();
        caret_pos = this.$inputor.caret('pos');
        subtext = content.slice(0, caret_pos);
        query = this.callbacks("matcher").call(this, this.at, subtext, this.get_opt('start_with_space'));
        if (typeof query === "string" && query.length <= this.get_opt('max_len', 20)) {
          start = caret_pos - query.length;
          end = start + query.length;
          this.pos = start;
          query = {
            'text': query.toLowerCase(),
            'head_pos': start,
            'end_pos': end
          };
          this.trigger("matched", [this.at, query.text]);
        } else {
          this.view.hide();
        }
        return this.query = query;
      };

      Controller.prototype.rect = function() {
        var c, scale_bottom;
        if (!(c = this.$inputor.caret('offset', this.pos - 1))) {
          return;
        }
        if (this.$inputor.attr('contentEditable') === 'true') {
          c = (this.cur_rect || (this.cur_rect = c)) || c;
        }
        scale_bottom = document.selection ? 0 : 2;
        return {
          left: c.left,
          top: c.top,
          bottom: c.top + c.height + scale_bottom
        };
      };

      Controller.prototype.reset_rect = function() {
        if (this.$inputor.attr('contentEditable') === 'true') {
          return this.cur_rect = null;
        }
      };

      Controller.prototype.mark_range = function() {
        this.range = this.get_range();
        return this.ie_range = this.get_ie_range();
      };

      Controller.prototype.clear_range = function() {
        return this.range = null;
      };

      Controller.prototype.get_range = function() {
    	  
        return this.range || (this.oWindow.frameElement.contentWindow.getSelection ? this.oWindow.frameElement.contentWindow.getSelection().getRangeAt(0) : void 0);
      };

      Controller.prototype.get_ie_range = function() {
        return this.ie_range || (this.oDocument.selection ? this.oDocument.selection.createRange() : void 0);
      };

      Controller.prototype.insert_content_for = function($li) {
        var data, data_value, tpl;
        data_value = $li.data('value');
        tpl = this.get_opt('insert_tpl');
        if (this.$inputor.is('textarea, input') || !tpl) {
          return data_value;
        }
        data = $.extend({}, $li.data('item-data'), {
          'atwho-data-value': data_value,
          'atwho-at': this.at
        });
        return this.callbacks("tpl_eval").call(this, tpl, data);
      };

      Controller.prototype.insert = function(content, $li) {
        var $inputor, $insert_node, class_name, content_node, insert_node, pos, range, sel, source, start_str, text;
        $inputor = this.$inputor;
        if ($inputor.attr('contentEditable') === 'true') {
          class_name = "atwho-view-flag atwho-view-flag-" + (this.get_opt('alias') || this.at);
          content_node = "" + content;
          
          //insert_node = "<span contenteditable='false' class='" + class_name + "'>" + content_node + "</span>";
          // 只保留<span>xxx</span>形式的内容。不再嵌套在 span 中了。
          insert_node = $("<span>"+content+"</span>").append('&nbsp;');
          $insert_node = $(insert_node).data('atwho-data-item', $li.data('item-data'));
          if (this.oDocument.selection) {
            $insert_node = $("<span contenteditable='true'></span>").html($insert_node);
          }
        }
        if ($inputor.is('textarea, input')) {
          content = '' + content;
          source = $inputor.val();
          start_str = source.slice(0, Math.max(this.query.head_pos - this.at.length, 0));
          text = "" + start_str + content + " " + (source.slice(this.query['end_pos'] || 0));
          $inputor.val(text);
          $inputor.caret('pos', start_str.length + content.length + 1);
        } else if (range = this.get_range()) {
          pos = range.startOffset - (this.query.end_pos - this.query.head_pos) - this.at.length;
          range.setStart(range.endContainer, Math.max(pos, 0));
          range.setEnd(range.endContainer, range.endOffset);
          range.deleteContents();
//        range.insertNode($insert_node[0]);
          
          var iframeDocument = this.oWindow.frameElement.contentWindow.document;
          var span = iframeDocument.createElement('span'); 
          span.innerHTML = $insert_node[0].innerHTML;
          range.insertNode(span);
          
          range.collapse(false);
          sel = this.oWindow.frameElement.contentWindow.getSelection();
          sel.removeAllRanges();
          sel.addRange(range);
        } else if (range = this.get_ie_range()) {
          //var moveStart = this.query.end_pos - this.query.head_pos - this.at.length;
          //解决IE8下面@时出现前缀内容
          var moveStart = 0 - this.query.text.length - this.at.length;
          range.moveStart('character', moveStart);
          content_node = content_node + "<span>&nbsp;</span>";
          
          range.pasteHTML(content_node);
          range.collapse(false);
          range.select();
        }
        $inputor.focus();
        return $inputor.change();
      };

      Controller.prototype.render_view = function(data) {
        var search_key;
        search_key = this.get_opt("search_key");
        data = this.callbacks("sorter").call(this, this.query.text, data.slice(0, 1001), search_key);
        return this.view.render(data.slice(0, this.get_opt('limit')));
      };

      Controller.prototype.look_up = function() {
        var query, _callback;
        if (!(query = this.catch_query())) {
          return;
        }
        _callback = function(data) {
          if (data && data.length > 0) {
            return this.render_view(data);
          } else {
            return this.view.hide();
          }
        };
        this.model.query(query.text, $.proxy(_callback, this));
        return query;
      };

      return Controller;

    })();
    Model = (function() {

      function Model(context) {
        this.context = context;
        this.at = this.context.at;
        this.storage = this.context.$inputor;
      }

      Model.prototype.saved = function() {
        return this.fetch() > 0;
      };

      Model.prototype.query = function(query, callback) {
        var data, search_key, _remote_filter;
        data = this.fetch();
        search_key = this.context.get_opt("search_key");
        data = this.context.callbacks('filter').call(this.context, query, data, search_key) || [];
        _remote_filter = this.context.callbacks('remote_filter');
        if (data.length > 0 || (!_remote_filter && data.length === 0)) {
          return callback(data);
        } else {
          return _remote_filter.call(this.context, query, callback);
        }
      };

      Model.prototype.fetch = function() {
        return this.storage.data(this.at) || [];
      };

      Model.prototype.save = function(data) {
        return this.storage.data(this.at, this.context.callbacks("before_save").call(this.context, data || []));
      };

      Model.prototype.load = function(data) {
        if (!(this.saved() || !data)) {
          return this._load(data);
        }
      };

      Model.prototype.reload = function(data) {
        return this._load(data);
      };

      Model.prototype._load = function(data) {
        var _this = this;
        if (typeof data === "string") {
          return $.ajax(data, {
            dataType: "json"
          }).done(function(data) {
            return _this.save(data);
          });
        } else {
          return this.save(data);
        }
      };

      return Model;

    })();
    View = (function() {

      function View(context) {
        this.context = context;
        this.$el = $("<div class='atwho-view'><div class='default_tip'>输入昵称或选择最近@过的人</div><ul class='atwho-view-ul'></ul></div>");
        this.timeout_id = null;
        this.context.$el.append(this.$el);
        this.bind_event();
      }

      View.prototype.init = function() {
        var id;
        id = this.context.get_opt("alias") || this.context.at.charCodeAt(0);
        return this.$el.attr({
          'id': "at-view-" + id
        });
      };

      View.prototype.bind_event = function() {
        var $menu,
          _this = this;
        $menu = this.$el.find('ul');
        $menu.on('mouseenter.atwho-view', 'li', function(e) {
          $menu.find('.cur').removeClass('cur');
          return $(e.currentTarget).addClass('cur');
        }).on('click', function(e) {
          _this.choose();
          return e.preventDefault();
        });
        return this.$el.on('mouseenter.atwho-view', 'ul', function(e) {
          return _this.context.mark_range();
        }).on('mouseleave.atwho-view', 'ul', function(e) {
          return _this.context.clear_range();
        });
      };

      View.prototype.visible = function() {
        return this.$el.is(":visible");
      };

      View.prototype.choose = function() {
        var $li, content;
        $li = this.$el.find(".cur");
        content = this.context.insert_content_for($li);
        this.context.insert(this.context.callbacks("before_insert").call(this.context, content, $li), $li);
        this.context.trigger("inserted", [$li]);
        return this.hide();
      };

      View.prototype.reposition = function(rect) {
        var offset;
        if (rect.bottom + this.$el.height() - $(window).scrollTop() > $(window).height()) {
          rect.bottom = rect.top - this.$el.height();
        }
        offset = {
          left: rect.left,
          top: rect.bottom
        };
        this.$el.offset(offset);
        return this.context.trigger("reposition", [offset]);
      };

      View.prototype.next = function() {
        var cur, next;
        cur = this.$el.find('.cur').removeClass('cur');
        next = cur.next();
        if (!next.length) {
          next = this.$el.find('li:first');
        }
        return next.addClass('cur');
      };

      View.prototype.prev = function() {
        var cur, prev;
        cur = this.$el.find('.cur').removeClass('cur');
        prev = cur.prev();
        if (!prev.length) {
          prev = this.$el.find('li:last');
        }
        return prev.addClass('cur');
      };

      View.prototype.showLabel = function () {
        this.$el.find('.default_tip').show();
      };

      View.prototype.hideLabel = function () {
        this.$el.find('.default_tip').hide();
      };

      View.prototype.clearItems = function () {
        this.$el.find('ul').empty();
      };

      View.prototype.show = function() {
        var rect;
        if (!this.visible()) {
          this.$el.show();
        }
        if (rect = this.context.rect()) {
          return this.reposition(rect);
        }
      };

      View.prototype.hide = function(time) {
        var callback,
          _this = this;
        if (isNaN(time && this.visible())) {
          this.context.reset_rect();
          return this.$el.hide();
        } else {
          callback = function() {
            return _this.hide();
          };
          clearTimeout(this.timeout_id);
          return this.timeout_id = setTimeout(callback, time);
        }
      };

      View.prototype.render = function(list) {
        var $li, $ul, item, li, tpl, _i, _len;
        if (!($.isArray(list) && list.length > 0)) {
          this.hide();
          return;
        }
        this.$el.find('ul').empty();
        $ul = this.$el.find('ul');
        tpl = this.context.get_opt('tpl');
        for (_i = 0, _len = list.length; _i < _len; _i++) {
          item = list[_i];
          item = $.extend({}, item, {
            'atwho-at': this.context.at
          });
          li = this.context.callbacks("tpl_eval").call(this.context, tpl, item);
          $li = $(this.context.callbacks("highlighter").call(this.context, li, this.context.query.text));
          $li.data("item-data", item);
          $ul.append($li);
        }
        this.show();
        return $ul.find("li:first").addClass("cur");
      };

      return View;

    })();
    KEY_CODE = {
      DOWN: 40,
      UP: 38,
      ESC: 27,
      TAB: 9,
      ENTER: 13
    };
    DEFAULT_CALLBACKS = {
      before_save: function(data) {
        var item, _i, _len, _results;
        if (!$.isArray(data)) {
          return data;
        }
        _results = [];
        for (_i = 0, _len = data.length; _i < _len; _i++) {
          item = data[_i];
          if ($.isPlainObject(item)) {
            _results.push(item);
          } else {
            _results.push({
              name: item
            });
          }
        }
        return _results;
      },
      matcher: function(flag, subtext, should_start_with_space) {
        var match, regexp;
        flag = flag.replace(/[\-\[\]\/\{\}\(\)\*\+\?\.\\\^\$\|]/g, "\\$&");
        if (should_start_with_space) {
          flag = '(?:^|\\s)' + flag;
        }
        regexp = new RegExp(flag + '([A-Za-z0-9_\+\-]*)$|' + flag + '([^\\x00-\\xff]*)$', 'gi');
        match = regexp.exec(subtext);
        if (match) {
          return match[2] || match[1];
        } else {
          return null;
        }
      },
      filter: function(query, data, search_key) {
        var item, _i, _len, _results;
        _results = [];
        for (_i = 0, _len = data.length; _i < _len; _i++) {
          item = data[_i];
          if (~item[search_key].toLowerCase().indexOf(query)) {
            _results.push(item);
          }
        }
        return _results;
      },
      remote_filter: null,
      sorter: function(query, items, search_key) {
        var item, _i, _len, _results;
        if (!query) {
          return items;
        }
        _results = [];
        for (_i = 0, _len = items.length; _i < _len; _i++) {
          item = items[_i];
          item.atwho_order = item[search_key].toLowerCase().indexOf(query);
          if (item.atwho_order > -1) {
            _results.push(item);
          }
        }
        return _results.sort(function(a, b) {
          return a.atwho_order - b.atwho_order;
        });
      },
      tpl_eval: function(tpl, map) {
        try {
          return tpl.replace(/\$\{([^\}]*)\}/g, function(tag, key, pos) {
            return map[key];
          });
        } catch (error) {
          return "";
        }
      },
      highlighter: function(li, query) {
        var regexp;
        if (!query) {
          return li;
        }
        regexp = new RegExp(">\\s*(\\w*)(" + query.replace("+", "\\+") + ")(\\w*)\\s*<", 'ig');
        return li.replace(regexp, function(str, $1, $2, $3) {
          return '> ' + $1 + '<strong>' + $2 + '</strong>' + $3 + ' <';
        });
      },
      before_insert: function(value, $li) {
        return value;
      }
    };
    Api = {
      load: function(at, data) {
        var c;
        if (c = this.controller(at)) {
          return c.model.load(data);
        }
      },
      getInsertedItemsWithIDs: function(at) {
        var c, ids, items;
        if (!(c = this.controller(at))) {
          return [null, null];
        }
        if (at) {
          at = "-" + (c.get_opt('alias') || c.at);
        }
        ids = [];
        items = $.map(this.$inputor.find("span.atwho-view-flag" + (at || "")), function(item) {
          var data;
          data = $(item).data('atwho-data-item');
          if (ids.indexOf(data.id) > -1) {
            return;
          }
          if (data.id) {
            ids.push = data.id;
          }
          return data;
        });
        return [ids, items];
      },
      getInsertedItems: function(at) {
        return Api.getInsertedItemsWithIDs.apply(this, [at])[1];
      },
      getInsertedIDs: function(at) {
        return Api.getInsertedItemsWithIDs.apply(this, [at])[0];
      },
      run: function() {
        return this.dispatch();
      }
    };
    Atwho = {
      init: function(options) {
        var $this, app;
        app = ($this = $(this)).data("atwho");
        if (!app) {
          $this.data('atwho', (app = new App(this)));
        }
        app.reg(options.at, options);
        return this;
      }
    };
    $CONTAINER = $("<div id='atwho-container'></div>");
    $.fn.atwho = function(method) {
      var result, _args;
      _args = arguments;
      $('body').append($CONTAINER);
      result = null;
      this.filter('textarea, input, [contenteditable=true]').each(function() {
        var app;
        if (typeof method === 'object' || !method) {
          if ((typeof method === 'object' && $(this).attr('data-disable') === method.at)) return;
          return Atwho.init.apply(this, _args);
        } else if (Api[method]) {
          if (app = $(this).data('atwho')) {
            return result = Api[method].apply(app, Array.prototype.slice.call(_args, 1));
          }
        } else {
          return $.error("Method " + method + " does not exist on jQuery.caret");
        }
      });
      return result || this;
    };
    return $.fn.atwho["default"] = {
      at: void 0,
      alias: void 0,
      data: null,
      tpl: "<li data-value='${atwho-at}${name}'>${name}</li>",
      insert_tpl: "<span>${atwho-data-value}</span>",
      callbacks: DEFAULT_CALLBACKS,
      search_key: "name",
      start_with_space: true,
      limit: 5,
      max_len: 20,
      display_timeout: 300
    };
  });

}).call(this);

/**
*  Ajax Autocomplete for jQuery, version 1.2.9
*  (c) 2013 Tomas Kirda
*
*  Ajax Autocomplete for jQuery is freely distributable under the terms of an MIT-style license.
*  For details, see the web site: https://github.com/devbridge/jQuery-Autocomplete
*
*/

/*jslint  browser: true, white: true, plusplus: true */
/*global define, window, document, jQuery */

// Expose plugin as an AMD module if AMD loader is present:
(function (factory) {
    'use strict';
    if (typeof define === 'function' && define.amd) {
        // AMD. Register as an anonymous module.
        define(['jquery'], factory);
    } else {
        // Browser globals
        factory(jQuery);
    }
}(function ($) {
    'use strict';

    var
        utils = (function () {
            return {
                escapeRegExChars: function (value) {
                    return value.replace(/[\-\[\]\/\{\}\(\)\*\+\?\.\\\^\$\|]/g, "\\$&");
                },
                createNode: function (containerClass) {
                    var div = document.createElement('div');
                    div.className = containerClass;
                    div.style.position = 'absolute';
                    div.style.display = 'none';
                    return div;
                }
            };
        }()),

        keys = {
            ESC: 27,
            TAB: 9,
            RETURN: 13,
            LEFT: 37,
            UP: 38,
            RIGHT: 39,
            DOWN: 40
        };

    function Autocomplete(el, options) {
        var noop = function () { },
            that = this,
            defaults = {
                autoSelectFirst: false,
                appendTo: 'body',
                serviceUrl: null,
                lookup: null,
                onSelect: null,
                onEnter: null,
                width: 'auto',
                minChars: 1,
                maxHeight: 300,
                deferRequestBy: 0,
                params: {},
                formatResult: Autocomplete.formatResult,
                delimiter: null,
                zIndex: 9999,
                type: 'GET',
                noCache: false,
                onSearchStart: noop,
                onSearchComplete: noop,
                onSearchError: noop,
                containerClass: 'autocomplete-suggestions',
                tabDisabled: false,
                dataType: 'text',
                currentRequest: null,
                triggerSelectOnValidInput: true,
                lookupFilter: function (suggestion, originalQuery, queryLowerCase) {
                    return suggestion.value.toLowerCase().indexOf(queryLowerCase) !== -1;
                },
                paramName: 'query',
                transformResult: function (response) {
                    return typeof response === 'string' ? $.parseJSON(response) : response;
                }
            };

        // Shared variables:
        that.element = el;
        that.el = $(el);
        that.suggestions = [];
        that.badQueries = [];
        that.selectedIndex = -1;
        that.currentValue = that.element.value;
        that.intervalId = 0;
        that.cachedResponse = {};
        that.onChangeInterval = null;
        that.onChange = null;
        that.isLocal = false;
        that.suggestionsContainer = null;
        that.options = $.extend({}, defaults, options);
        that.classes = {
            selected: 'autocomplete-selected',
            suggestion: 'autocomplete-suggestion'
        };
        that.hint = null;
        that.hintValue = '';
        that.selection = null;

        // Initialize and set options:
        that.initialize();
        that.setOptions(options);
    }

    Autocomplete.utils = utils;

    $.Autocomplete = Autocomplete;

    Autocomplete.formatResult = function (suggestion, currentValue) {
        var pattern = '(' + utils.escapeRegExChars(currentValue) + ')';
        
        //hack
        var extractIndex = suggestion.value.indexOf("[");
        if(extractIndex != -1) {
        	var rs = suggestion.value.substr(0, extractIndex).replace(new RegExp(pattern, 'gi'), '<strong>$1<\/strong>');
        	var after = suggestion.value.substr(extractIndex);
        	return rs + after;
        } else {
        	return suggestion.value.replace(new RegExp(pattern, 'gi'), '<strong>$1<\/strong>');
        }
        
//        return suggestion.value.replace(new RegExp(pattern, 'gi'), '<strong>$1<\/strong>');
    };

    Autocomplete.prototype = {

        killerFn: null,

        initialize: function () {
            var that = this,
                suggestionSelector = '.' + that.classes.suggestion,
                selected = that.classes.selected,
                options = that.options,
                container;

            // Remove autocomplete attribute to prevent native suggestions:
            that.element.setAttribute('autocomplete', 'off');

            that.killerFn = function (e) {
                if ($(e.target).closest('.' + that.options.containerClass).length === 0) {
                    that.killSuggestions();
                    that.disableKillerFn();
                }
            };

            that.suggestionsContainer = Autocomplete.utils.createNode(options.containerClass);

            container = $(that.suggestionsContainer);

            container.appendTo(options.appendTo);

            // Only set width if it was provided:
            if (options.width !== 'auto') {
                container.width(options.width);
            }

            // Listen for mouse over event on suggestions list:
            container.on('mouseover.autocomplete', suggestionSelector, function () {
                that.activate($(this).data('index'));
            });

            // Deselect active element when mouse leaves suggestions container:
            container.on('mouseout.autocomplete', function () {
                that.selectedIndex = -1;
                container.children('.' + selected).removeClass(selected);
            });

            // Listen for click event on suggestions list:
            container.on('click.autocomplete', suggestionSelector, function () {
                that.select($(this).data('index'));
            });

            that.fixPosition();

            that.fixPositionCapture = function () {
                if (that.visible) {
                    that.fixPosition();
                }
            };

            $(window).on('resize.autocomplete', that.fixPositionCapture);

            that.el.on('keydown.autocomplete', function (e) { that.onKeyPress(e); });
            that.el.on('keyup.autocomplete', function (e) { that.onKeyUp(e); });
            that.el.on('blur.autocomplete', function () { that.onBlur(); });
            that.el.on('focus.autocomplete', function () { that.onFocus(); });
            that.el.on('change.autocomplete', function (e) { that.onKeyUp(e); });
        },

        onFocus: function () {
            var that = this;
            that.fixPosition();
            if (that.options.minChars <= that.el.val().length) {
                that.onValueChange();
            }
        },

        onBlur: function () {
            this.enableKillerFn();

        },
        setOptions: function (suppliedOptions) {
            var that = this,
                options = that.options;

            $.extend(options, suppliedOptions);

            that.isLocal = $.isArray(options.lookup);

            if (that.isLocal) {
                options.lookup = that.verifySuggestionsFormat(options.lookup);
            }

            // Adjust height, width and z-index:
            $(that.suggestionsContainer).css({
                'max-height': options.maxHeight + 'px',
                'width': options.width + 'px',
                'z-index': options.zIndex
            });
        },

        clearCache: function () {
            this.cachedResponse = {};
            this.badQueries = [];
        },

        clear: function () {
            this.clearCache();
            this.currentValue = '';
            this.suggestions = [];
        },

        disable: function () {
            var that = this;
            that.disabled = true;
            if (that.currentRequest) {
                that.currentRequest.abort();
            }
        },

        enable: function () {
            this.disabled = false;
        },

        fixPosition: function () {
            var that = this,
                offset,
                styles;

            // Don't adjsut position if custom container has been specified:
            if (that.options.appendTo !== 'body') {
                return;
            }

            offset = that.el.offset();

            styles = {
                top: (offset.top + that.el.outerHeight()) + 'px',
                left: offset.left + 'px'
            };

            if (that.options.width === 'auto') {
                styles.width = (that.el.outerWidth() - 2) + 'px';
            }

            $(that.suggestionsContainer).css(styles);
        },

        enableKillerFn: function () {
            var that = this;
            $(document).on('click.autocomplete', that.killerFn);
        },

        disableKillerFn: function () {
            var that = this;
            $(document).off('click.autocomplete', that.killerFn);
        },

        killSuggestions: function () {
            var that = this;
            that.stopKillSuggestions();
            that.intervalId = window.setInterval(function () {
                that.hide();
                that.stopKillSuggestions();
            }, 50);
        },

        stopKillSuggestions: function () {
            window.clearInterval(this.intervalId);
        },

        isCursorAtEnd: function () {
            var that = this,
                valLength = that.el.val().length,
                selectionStart = that.element.selectionStart,
                range;

            if (typeof selectionStart === 'number') {
                return selectionStart === valLength;
            }
            if (document.selection) {
                range = document.selection.createRange();
                range.moveStart('character', -valLength);
                return valLength === range.text.length;
            }
            return true;
        },

        onKeyPress: function (e) {
            var that = this;

            // If suggestions are hidden and user presses arrow down, display suggestions:
            if (!that.disabled && !that.visible && e.which === keys.DOWN && that.currentValue) {
                that.suggest();
                return;
            }

            if (that.disabled || !that.visible) {
                if (e.which === keys.RETURN) {
                    setTimeout(function () {
                        that.onEnter();
                    }, 100);
                }
                return;
            }

            switch (e.which) {
                case keys.ESC:
                    that.el.val(that.currentValue);
                    that.hide();
                    break;
                case keys.RIGHT:
                    if (that.hint && that.options.onHint && that.isCursorAtEnd()) {
                        that.selectHint();
                        break;
                    }
                    return;
                case keys.TAB:
                    if (that.hint && that.options.onHint) {
                        that.selectHint();
                        return;
                    }
                    // Fall through to RETURN
                case keys.RETURN:
                    if (that.selectedIndex === -1) {
                        that.hide();
                        return;
                    }
                    that.select(that.selectedIndex);
                    if (e.which === keys.TAB && that.options.tabDisabled === false) {
                        return;
                    }
                    break;
                case keys.UP:
                    that.moveUp();
                    break;
                case keys.DOWN:
                    that.moveDown();
                    break;
                default:
                    return;
            }

            // Cancel event if function did not return:
            e.stopImmediatePropagation();
            e.preventDefault();
        },

        onKeyUp: function (e) {
            var that = this;

            if (that.disabled) {
                return;
            }

            switch (e.which) {
                case keys.UP:
                case keys.DOWN:
                    return;
            }

            clearInterval(that.onChangeInterval);

            if (that.currentValue !== that.el.val()) {
                that.findBestHint();
                if (that.options.deferRequestBy > 0) {
                    // Defer lookup in case when value changes very quickly:
                    that.onChangeInterval = setInterval(function () {
                        that.onValueChange();
                    }, that.options.deferRequestBy);
                } else {
                    that.onValueChange();
                }
            }
        },

        onValueChange: function () {
            var that = this,
                options = that.options,
                value = that.el.val(),
                query = that.getQuery(value),
                index;

            if (that.selection) {
                that.selection = null;
                (options.onInvalidateSelection || $.noop).call(that.element);
            }

            clearInterval(that.onChangeInterval);
            that.currentValue = value;
            that.selectedIndex = -1;

            // Check existing suggestion for the match before proceeding:
            if (options.triggerSelectOnValidInput) {
                index = that.findSuggestionIndex(query);
                if (index !== -1) {
                    that.select(index);
                    return;
                }
            }

            if (query.length < options.minChars) {
                that.hide();
            } else {
                that.getSuggestions(query);
            }
        },

        findSuggestionIndex: function (query) {
            var that = this,
                index = -1,
                queryLowerCase = query.toLowerCase();

            $.each(that.suggestions, function (i, suggestion) {
                if (suggestion.value.toLowerCase() === queryLowerCase) {
                    index = i;
                    return false;
                }
            });

            return index;
        },

        getQuery: function (value) {
            var delimiter = this.options.delimiter,
                parts;

            if (!delimiter) {
                return value;
            }
            parts = value.split(delimiter);
            return $.trim(parts[parts.length - 1]);
        },

        getSuggestionsLocal: function (query) {
            var that = this,
                options = that.options,
                queryLowerCase = query.toLowerCase(),
                filter = options.lookupFilter,
                limit = parseInt(options.lookupLimit, 10),
                data;

            data = {
                suggestions: $.grep(options.lookup, function (suggestion) {
                    return filter(suggestion, query, queryLowerCase);
                })
            };

            if (limit && data.suggestions.length > limit) {
                data.suggestions = data.suggestions.slice(0, limit);
            }

            return data;
        },

        getSuggestions: function (q) {
            var response,
                that = this,
                options = that.options,
                serviceUrl = options.serviceUrl,
                data,
                cacheKey;

            options.params[options.paramName] = q;
            data = options.ignoreParams ? null : options.params;

            if (that.isLocal) {
                response = that.getSuggestionsLocal(q);
            } else {
                if ($.isFunction(serviceUrl)) {
                    serviceUrl = serviceUrl.call(that.element, q);
                }
                cacheKey = serviceUrl + '?' + $.param(data || {});
                response = that.cachedResponse[cacheKey];
            }

            if (response && $.isArray(response.suggestions)) {
                that.suggestions = response.suggestions;
                that.suggest();
            } else if (!that.isBadQuery(q)) {
                if (options.onSearchStart.call(that.element, options.params) === false) {
                    return;
                }
                if (that.currentRequest) {
                    that.currentRequest.abort();
                }
                that.currentRequest = $.ajax({
                    url: serviceUrl,
                    data: data,
                    type: options.type,
                    dataType: options.dataType
                }).done(function (data) {
                    that.currentRequest = null;
                    that.processResponse(data, q, cacheKey);
                    options.onSearchComplete.call(that.element, q);
                }).fail(function (jqXHR, textStatus, errorThrown) {
                    options.onSearchError.call(that.element, q, jqXHR, textStatus, errorThrown);
                });
            }
        },

        isBadQuery: function (q) {
            var badQueries = this.badQueries,
                i = badQueries.length;

            while (i--) {
                if (q.indexOf(badQueries[i]) === 0) {
                    return true;
                }
            }

            return false;
        },

        hide: function () {
            var that = this;
            that.visible = false;
            that.selectedIndex = -1;
            $(that.suggestionsContainer).hide();
            that.signalHint(null);
        },

        suggest: function () {
            if (this.suggestions.length === 0) {
                this.hide();
                return;
            }

            var that = this,
                options = that.options,
                formatResult = options.formatResult,
                value = that.getQuery(that.currentValue),
                className = that.classes.suggestion,
                classSelected = that.classes.selected,
                container = $(that.suggestionsContainer),
                beforeRender = options.beforeRender,
                html = '',
                index,
                width;

            if (options.triggerSelectOnValidInput) {
                index = that.findSuggestionIndex(value);
                if (index !== -1) {
                    that.select(index);
                    return;
                }
            }

            // Build suggestions inner HTML:
            $.each(that.suggestions, function (i, suggestion) {
                html += '<div class="' + className + '" data-index="' + i + '">' + formatResult(suggestion, value) + '</div>';
            });

            // If width is auto, adjust width before displaying suggestions,
            // because if instance was created before input had width, it will be zero.
            // Also it adjusts if input width has changed.
            // -2px to account for suggestions border.
            if (options.width === 'auto') {
                width = that.el.outerWidth() - 2;
                container.width(width > 0 ? width : 300);
            }

            container.html(html);

            // Select first value by default:
            if (options.autoSelectFirst) {
                that.selectedIndex = 0;
                container.children().first().addClass(classSelected);
            }

            if ($.isFunction(beforeRender)) {
                beforeRender.call(that.element, container);
            }

            container.show();
            that.visible = true;

            that.findBestHint();
        },

        findBestHint: function () {
            var that = this,
                value = that.el.val().toLowerCase(),
                bestMatch = null;

            if (!value) {
                return;
            }

            $.each(that.suggestions, function (i, suggestion) {
                var foundMatch = suggestion.value.toLowerCase().indexOf(value) === 0;
                if (foundMatch) {
                    bestMatch = suggestion;
                }
                return !foundMatch;
            });

            that.signalHint(bestMatch);
        },

        signalHint: function (suggestion) {
            var hintValue = '',
                that = this;
            if (suggestion) {
                hintValue = that.currentValue + suggestion.value.substr(that.currentValue.length);
            }
            if (that.hintValue !== hintValue) {
                that.hintValue = hintValue;
                that.hint = suggestion;
                (this.options.onHint || $.noop)(hintValue);
            }
        },

        verifySuggestionsFormat: function (suggestions) {
            // If suggestions is string array, convert them to supported format:
            if (suggestions.length && typeof suggestions[0] === 'string') {
                return $.map(suggestions, function (value) {
                    return { value: value, data: null };
                });
            }

            return suggestions;
        },

        processResponse: function (response, originalQuery, cacheKey) {
            var that = this,
                options = that.options,
                result = options.transformResult(response, originalQuery);

            result.suggestions = that.verifySuggestionsFormat(result.suggestions);

            // Cache results if cache is not disabled:
            if (!options.noCache) {
                that.cachedResponse[cacheKey] = result;
                if (result.suggestions.length === 0) {
                    that.badQueries.push(cacheKey);
                }
            }

            // Return if originalQuery is not matching current query:
            if (originalQuery !== that.getQuery(that.currentValue)) {
                return;
            }

            that.suggestions = result.suggestions;
            that.suggest();
        },

        activate: function (index) {
            var that = this,
                activeItem,
                selected = that.classes.selected,
                container = $(that.suggestionsContainer),
                children = container.children();

            container.children('.' + selected).removeClass(selected);

            that.selectedIndex = index;

            if (that.selectedIndex !== -1 && children.length > that.selectedIndex) {
                activeItem = children.get(that.selectedIndex);
                $(activeItem).addClass(selected);
                return activeItem;
            }

            return null;
        },

        selectHint: function () {
            var that = this,
                i = $.inArray(that.hint, that.suggestions);

            that.select(i);
        },

        select: function (i) {
            var that = this;
            that.hide();
            that.onSelect(i);
        },

        moveUp: function () {
            var that = this;

            if (that.selectedIndex === -1) {
                return;
            }

            if (that.selectedIndex === 0) {
                $(that.suggestionsContainer).children().first().removeClass(that.classes.selected);
                that.selectedIndex = -1;
                that.el.val(that.currentValue);
                that.findBestHint();
                return;
            }

            that.adjustScroll(that.selectedIndex - 1);
        },

        moveDown: function () {
            var that = this;

            if (that.selectedIndex === (that.suggestions.length - 1)) {
                return;
            }

            that.adjustScroll(that.selectedIndex + 1);
        },

        adjustScroll: function (index) {
            var that = this,
                activeItem = that.activate(index),
                offsetTop,
                upperBound,
                lowerBound,
                heightDelta = 25;

            if (!activeItem) {
                return;
            }

            offsetTop = activeItem.offsetTop;
            upperBound = $(that.suggestionsContainer).scrollTop();
            lowerBound = upperBound + that.options.maxHeight - heightDelta;

            if (offsetTop < upperBound) {
                $(that.suggestionsContainer).scrollTop(offsetTop);
            } else if (offsetTop > lowerBound) {
                $(that.suggestionsContainer).scrollTop(offsetTop - that.options.maxHeight + heightDelta);
            }

            that.el.val(that.getValue(that.suggestions[index].value));
            that.signalHint(null);
        },

        onSelect: function (index) {
            var that = this,
                onSelectCallback = that.options.onSelect,
                suggestion = that.suggestions[index];

            that.currentValue = that.getValue(suggestion.value);
            that.el.val(that.currentValue);
            that.signalHint(null);
            that.suggestions = [];
            that.selection = suggestion;

            if ($.isFunction(onSelectCallback)) {
                onSelectCallback.call(that.element, suggestion);
            }
        },

        onEnter: function () {
            var that = this,
                onEnterCallback = that.options.onEnter;

            if ($.isFunction(onEnterCallback)) {
                onEnterCallback.call(that.element);
            }
        },

        getValue: function (value) {
            var that = this,
                delimiter = that.options.delimiter,
                currentValue,
                parts;

            if(value) {
            	value = value.replace('<span class="match-onsale">[在售]</span>', "");
            	value = value.replace(/<span .+?>\d+-\d+-\d+<\/span>/g, '');
            }
            if (!delimiter) {
                return value;
            }

            currentValue = that.currentValue;
            parts = currentValue.split(delimiter);

            if (parts.length === 1) {
                return value;
            }

            return currentValue.substr(0, currentValue.length - parts[parts.length - 1].length) + value;
        },

        dispose: function () {
            var that = this;
            that.el.off('.autocomplete').removeData('autocomplete');
            that.disableKillerFn();
            $(window).off('resize.autocomplete', that.fixPositionCapture);
            $(that.suggestionsContainer).remove();
        }
    };

    // Create chainable jQuery plugin:
    $.fn.autocomplete = function (options, args) {
        var dataKey = 'autocomplete';
        // If function invoked without argument return
        // instance of the first matched element:
        if (arguments.length === 0) {
            return this.first().data(dataKey);
        }

        return this.each(function () {
            var inputElement = $(this),
                instance = inputElement.data(dataKey);

            if (typeof options === 'string') {
                if (instance && typeof instance[options] === 'function') {
                    instance[options](args);
                }
            } else {
                // If instance already exists, destroy it:
                if (instance && instance.dispose) {
                    instance.dispose();
                }
                instance = new Autocomplete(this, options);
                inputElement.data(dataKey, instance);
            }
        });
    };
}));

$(function() {
	(function ($) {
	    
	    var weiboInput     = $('.form-control'), //$('#weibo_input_detail'),
	        weiboInputs    = null,
	        // gameBtn        = $('#game_btn'),
	        searchUserUrl  = 'http://www.davcai.com/ajax_search_user',
	        searchMatchUrl = 'http://www.davcai.com/ajax_search_match',
	        userNameList   = {},
	        matchList      = {},
	        gameNames      = {
	            JCZQ: '竞彩足球',
	            JCLQ: '竞彩篮球',
	            CTZC: '传统足彩',
	            BJDC: '北京单场'
	        },
	        gameMarks      = {
	            JCZQ: 'JZ',
	            JCLQ: 'JL',
	            CTZC: 'CZ',
	            BJDC: 'BD'
	        },
	        matchWrap, uiOverlay,
	        userItem, matchItem, _tmpList;

	    window.WB_API = {};

	    WB_API.addAtWhoEvent = function (input) {
	        addSearchEvent($(input));
	    };
	    
	    WB_API.showMatchWrapUEditor = showMatchWrapUEditor;

	    WB_API.addNickNameAtWho = addNickNameAtWho;
	    
	    WB_API.showMatchAreaDelegateEvents = addDelegateEvents;
	    WB_API.showMatchAreaDelegateEventsWith = addDelegateEventsWith;
	    
	    try {
	    	WB_API._wbEditor = _wbEditor;
		} catch (e) {
			WB_API._wbEditor = null;
		}
	    
	    function addNickNameAtWho(editor){
	    	$(editor).atwho({
	            at: "@",
	            start_with_space: false,
	            callbacks: {
	                remote_filter: function (query, render_view) {
	                    var thisVal = query,
	                    self = $(this);
	                    if( !self.data('active') ) {
	                        self.data('active', true);

	                        this.view.clearItems();
	                        if (thisVal === '') {
	                            this.view.show();
	                            this.view.showLabel();
	                            self.data('active', false);
	                            return;
	                        } else {
	                            this.view.hideLabel();
	                        }

	                        userItem = userNameList[thisVal];
	                        if(typeof userItem == "object" 
	                        	&& !(userItem instanceof Array)) {
	                            render_view(userItem);
	                            self.data('active', false);
	                        } else {
	                            if (self.xhr) {
	                                self.xhr.abort();
	                            }

	                            self.xhr = $.ajax({
	                                url: searchUserUrl + '?keys=' + encodeURIComponent(thisVal),
	                                dataType: 'jsonp',
	                                success: function (res) {
	                                    if (typeof res == 'object') {
	                                        _tmpList = remapUserList(res.users.results);
	                                        userNameList[thisVal] = _tmpList;
	                                        render_view(_tmpList);
	                                    };
	                                    
	                                    self.data('active', false);
	                                },
	                                error: function () {
	                                    self.data('active', false);
	                                }
	                            });
	                        }
	                    }                    
	                }
	            }
	        });
	    }
	    function addSearchEvent (input) {
	        var _input = input || weiboInput;
	        
	        _input.atwho({
	            at: '$',
	            start_with_space: false,
	            callbacks: {
	                remote_filter: function (query, render_view) {
	                	removeInputSymbol($(this));
	                	
	                    var thisVal = query,
	                    self = $(this);

	                    if (thisVal === '') {
	                        showMatchWrap(this);
	                    };
	                },

	                before_insert: function (value, $li) {
	                    return filterMatchResult(value);
	                }
	            }
	        })
	        .atwho({
	            at: '￥',
	            start_with_space: false,
	            callbacks: {
	                remote_filter: function (query, render_view) {
	                	removeInputSymbol($(this));
	                	
	                    var thisVal = query,
	                    self = $(this);
	
	                    if (thisVal === '') {
	                        showMatchWrap(this);
	                    };
	                },
	
	                before_insert: function (value, $li) {
	                    return filterMatchResult(value);
	                }
	            }
	        });
	    }
	    
	    function removeInputSymbol(jqElement) {
	    	var jqInput = jqElement[0].$inputor;
	    	var content = jqInput.val();
	    	var pattern = /(\$|￥)((\&nbsp;)*(<\w+\/>)*(<\/\w+>)*)$/;
        	var cont = content.replace(pattern, function(str, g1, g2){
        		return g2;
        	});
        	jqInput.val(cont);
	    }
	    
	    // for UEditor
	    function showMatchWrapUEditor (target) {
	        uiOverlay = uiOverlay || $('#ui_overlay');
	        if (!matchWrap) {
	            matchWrap = $('#match_wrap');
	            createAutocomplete();
	        };

	        matchWrap.data('targetInput', target);
	        uiOverlay.show();

	        var offset = target.offset();
	        matchWrap.css({
	            top: offset.top + target.height() + 16,
	            left: offset.left - 6
	        }).show().find('input').focus();

	        addRemoveMatchWrapEvent();
	        
	        setTimeout(function(){
	        	var content = _wbEditor.getContent();
	        	// 去掉已经输入的$/￥符号
	        	var pattern = /(\$|￥)((\&nbsp;)*(<\w+\/>)*(<\/\w+>)*)$/;
	        	var html = content.replace(pattern, function(str, g1, g2){
	        		return g2;
	        	});
	        	_wbEditor.setContent(html);
	        }, 100);
	    }
	    
	    function showMatchWrap (target) {
	        uiOverlay = uiOverlay || $('#ui_overlay');
	        if (!matchWrap) {
	            matchWrap = $('#match_wrap');
	            createAutocomplete();
	        };

	        matchWrap.data('targetInput', target);
	        uiOverlay.show();

	        var offset = target.$inputor.offset();
	        
	        matchWrap.css({
	            top: offset.top + target.$inputor.height() + 16,
	            left: offset.left - 6
	        }).show().find('input').focus();

	        addRemoveMatchWrapEvent();
	    }
	    
	    function addRemoveMatchWrapEvent () {
	        $('body').on('keydown.match', function (event) {
	            if (event.keyCode === 27) hideMatchWrap();
	        });
	    }

	    function removeRemoveMatchWrapEvent () {
	        $('body').off('keydown.match');
	    }

	    function addRemoveMatchWrapEvent () {
	        $('body').on('keydown', function (event) {
	            if (event.keyCode === 27) hideMatchWrap();
	        });
	    }
	    
	    function createAutocomplete () {
	        matchWrap.find('input').autocomplete({
	            serviceUrl: searchMatchUrl,
	            dataType: 'jsonp',
	            paramName: 'key',
	            transformResult: function (response, originalQuery) {
	                return {
	                    suggestions: remapMatchList(response)
	                };
	            },
	            onEnter: function () {
	                var content = '$' + $(this).val(),
	                    target  = matchWrap.data('targetInput');

	                try {
	                    target.insert(content);
	                } catch (e) {
	                	var insertContent = content.replace(/^\$/, '') + ' ';
	                    if (target.$inputor){
                    		target.$inputor.insertAtCaret(insertContent);
                    	}else{	// for UEditor hack
                    		_wbEditor.focus(true);
                    		_wbEditor.execCommand('inserthtml', insertContent);
                    	}
	                }

	                hideMatchWrap();
	            },
	            onSelect: function (suggestion) {
	                if (suggestion) {
	                    var content = filterMatchResult(suggestion.value, suggestion.source),
	                        target  = matchWrap.data('targetInput');

	                    try {
	                        target.insert(content);
	                    } catch (e) {
	                    	var insertContent = content.replace(/^\$/, '') + ' ';
	                    	if (target.$inputor){
	                    		target.$inputor.insertAtCaret(insertContent);
	                    	}else{	// for UEditor hack
	                    		_wbEditor.focus(true);
	                    		_wbEditor.execCommand('inserthtml', content);
	                    	}
	                    }

	                    hideMatchWrap();
	                };
	            }
	        });
	    }

	    function hideMatchWrap () {
	        if (matchWrap && uiOverlay) {
	            matchWrap.hide().find('input').val('');
	            uiOverlay.hide();
	        };

	        removeRemoveMatchWrapEvent();
	        if(WB_API._wbEditor) {
	        	_wbEditor.focus(true);
	        }
	    }

	    function isGameDead (time) {
	        if (!time) return true;

	        time = time.replace(/t/i, ' ');
	        var deadTime = new Date(time).getTime(),
	            now = new Date().getTime();

	        return now > deadTime;
	    }

	    function filterMatchResult (value, source) {
	        var result = '',
	            htmlTagReg = /<\/?[^>]*>/g,
	            sale = /\s\[在售\]/g,
	            matchTime = /\s\d+-\d+-\d+/g,
	            reg    = /\s*$/,
	            number = '';

	        if (source) {
	            number = '(' + getGameNumber(source) + ')';
	        };
	        
	        if(value == "$" || value == "￥") {
	        	result = value;
	        } else if (!!value) {
	            value += number;
	            if (reg.test(value)) value = value + ' ';
	            result = '$' + value.replace(reg, '$').
	            	replace(htmlTagReg, '').replace(sale, '').replace(matchTime, '');
	        } 

	        return result;
	    }

	    function remapUserList (list) {
	        return $.map(list, function (value, i) {
	            return {
	                name: value.nickName
	            }
	        })
	    }

	    function getGameNumber (value) {
	        if (!value) return '';

	        var mark = '',
	            number = '';
	        if(value.lotteryType) {
	        	mark = gameMarks[value.lotteryType.toUpperCase()];
	        }

	        switch (mark) {
	            case 'CZ' :
	                number = getCode();
	            break;

	            default :
	                number = getId();
	            break;
	        }

	        function getCode () {
	            var n = value.matchCode,
	                mid = value.matchId || 0;

	            value = null;
	            return n + (mid.length<2?('0'+mid):mid);
	        }

	        function getId () {
	            var n = '';
	            if (value.id) {
	                n = value.id.replace(/^20/, '');
	            };

	            value = null;
	            return n;
	        }

	        return mark + number;
	    }

	    function remapMatchList (list) {
	    	list = inFrontOfOnSaleMatch(list);
	        var label;
	        var resultList = $.map(list, function (value, i) {
	            var s = '';
	            // if (!isGameDead(value.entrustDeadline)) {
	            if (value.sale) {
	                s = '<span class="match-onsale">[在售]</span>';
	            } else {
	            	var dateStr = "";
	            	if(value.matchTime && value.matchTime.length > 10) {
	            		dateStr = value.matchTime.replace("T"," ").substr(0, 10);
	            	}
	            	s = value.matchTime ? '<span style="color:#646464;">' +dateStr+'</span>':"";
	            };
	            
	            label = gameNames[value.lotteryType.toUpperCase()] + ' ' + value.matchCode + ' ' + value.matchName + ' ' + s;
	            return {
	                source: value,
	                data: value.id,
	                value: label
	            };
	        });
	        var keyArr = [];
	        keyArr.push({"data":"", "source":{}, value:"$"});
	        keyArr.push({"data":"", "source":{}, value:"￥"});
	        
	        return keyArr.concat(resultList);
	    }

	    /**使在售的比赛按时间倒排放在前面，其他赛事记录顺序不变*/
	    function inFrontOfOnSaleMatch(list) {
	    	var sortedList = [];
	    	var onSaleList = [];
	    	$.map(list, function(value, i) {
	    		if (value.sale) {
	    			onSaleList.push(value);
	            } else {
	            	sortedList.push(value);
	            }
	    	});
	    	onSaleList.sort(sortOnSaleMatch);
	    	return onSaleList.concat(sortedList);
	    }
	    
	    function sortOnSaleMatch(eltFirst, eltSecond) {
	    	var eltFirstDeadTime = new Date(eltFirst.entrustDeadline.replace("T"," "));
	    	var eltSecondDeadTime = new Date(eltSecond.entrustDeadline.replace("T"," "));
	    	var firstTime = eltFirstDeadTime.getTime();
	    	var secondTime = eltSecondDeadTime.getTime();
	    	
	    	if(firstTime - secondTime == 0) {
	    		return 0;
	    	} else if(firstTime - secondTime > 0) {
	    		return 1;
	    	} else {
	    		return -1;
	    	}
	    }
	    
	    function getInputBySelector (selector) {
	        var target;
	        $(weiboInputs).each(function () {
	            if ($(this).is(selector)) target = $(this);
	        });

	        return target;
	    }

	    function addDelegateEvents () {
	        $('body').delegate('#match_wrap i.close, #ui_overlay', 'click', function(event) {
	            hideMatchWrap();
	        });
	        
            $('.game_show_btn').click(function(event) {
                var app = $(this).parent().parent().parent().parent().find('.form-control').data('atwho');
                var commentEditor = $(this).parent().parent().parent().find('.commentEditor').data('atwho');
                var weiboRealFollowEditor = $(this).closest("[weiboBetForm]").find('[weiboBetTextarea]').data('atwho');
                if(app || commentEditor || weiboRealFollowEditor){
                	if(commentEditor) {
                		app = commentEditor;
                	} else if(weiboRealFollowEditor){
                		app = weiboRealFollowEditor;
                	}
                	app.$inputor.insertAtCaret('$');
                	app && $.isFunction(app.dispatch) && app.dispatch();
                	showMatchWrap(app.controllers['$']);
                }else{
                	// 使用了 ueditor 的情况
               		// for 赛事框
               		showMatchWrapUEditor($(this));
                }
	        });
	    }
	    function addDelegateEventsWith ($context) {
	    	$('body').delegate('#match_wrap i.close, #ui_overlay', 'click', function(event) {
	    		hideMatchWrap();
	    	});
	    	
	    	$('.game_show_btn',$context).click(function(event) {
	    		var app = $(this).parent().parent().parent().parent().find('.form-control').data('atwho');
	    		var commentEditor = $(this).parent().parent().parent().find('.commentEditor').data('atwho');
	    		var weiboRealFollowEditor = $(this).closest("[weiboBetForm]").find('[weiboBetTextarea]').data('atwho');
	    		if(app || commentEditor || weiboRealFollowEditor){
	    			if(commentEditor) {
	    				app = commentEditor;
	    			} else if(weiboRealFollowEditor){
	    				app = weiboRealFollowEditor;
	    			}
	    			app.$inputor.insertAtCaret('$');
	    			app && $.isFunction(app.dispatch) && app.dispatch();
	    			showMatchWrap(app.controllers['$']);
	    		}else{
	    			// 使用了 ueditor 的情况
	    			// for 赛事框
	    			showMatchWrapUEditor($(this));
	    		}
	    	});
	    }

	    (function () {
	        addSearchEvent();
	    })();

	})(window.jQuery);



	(function($){
	    $.fn.extend({
	        insertAtCaret: function(myValue){
	            var $t=$(this)[0];
	            if (document.selection) {
	                this.focus();
	                sel = document.selection.createRange();
	                sel.text = myValue;
	                this.focus();
	            }
	            else 
	                if ($t.selectionStart || $t.selectionStart == '0') {
	                    var startPos = $t.selectionStart;
	                    var endPos = $t.selectionEnd;
	                    var scrollTop = $t.scrollTop;
	                    $t.value = $t.value.substring(0, startPos) + myValue + $t.value.substring(endPos, $t.value.length);
	                    this.focus();
	                    $t.selectionStart = startPos + myValue.length;
	                    $t.selectionEnd = startPos + myValue.length;
	                    $t.scrollTop = scrollTop;
	                }
	                else {
	                    this.value += myValue;
	                    this.focus();
	                }
	        }
	    });
	})(jQuery);
});
$(function() {
	//高亮当前访问页面的标签
	url = location.pathname;
	var leftMenuWrap = $(".scores_left_menu_wrap"); 
	if(url.indexOf("favoriateList") == 1) {
		leftMenuWrap.find(".s-favorite").css("background-position", "-774px -125px");
		leftMenuWrap.find(".s-favorite > a").css("color", "#E22319");
	} else if(url.indexOf("atMeList") == 1) {
		leftMenuWrap.find(".s-atMe").css("background-position", "-774px -224px");
		leftMenuWrap.find(".s-atMe > a").css("color", "#E22319");
	} else if(url.indexOf("cmnt_me") == 1) {
		leftMenuWrap.find(".s-cmnt-me").css("background-position", "-774px -274px");
		leftMenuWrap.find(".s-cmnt-me > a").css("color", "#E22319");
	} else if(url.indexOf("private_msg") == 1) {
		leftMenuWrap.find(".s-privateMsgs").css("background-position", "-774px -320px");
		leftMenuWrap.find(".s-privateMsgs > a").css("color", "#E22319");
	} else if(url.indexOf("rltship_findFollowings") == 1) {
		leftMenuWrap.find(".s-following").css("background-position", "-774px -375px");
		leftMenuWrap.find(".s-following > a").css("color", "#E22319");
	} else if(url.indexOf("rltship_findFollowers") == 1) {
		leftMenuWrap.find(".s-follower").css("background-position", "-774px -428px");
		leftMenuWrap.find(".s-follower > a").css("color", "#E22319");
	} else if(url.indexOf("home") == 1) {
		leftMenuWrap.find(".s-home").css("background-position", "-774px -73px");
		leftMenuWrap.find(".s-home > a").css("color", "#E22319");
	}
	
	bindIconSwitchClick();
});

//绑定单击首页标签切换事件
function bindIconSwitchClick() {
	$(".scores_left_menu_wrap").find("ul > li").click(function() {
		var href = $(this).find("a").attr("href");
		window.location.href = href;
	});
}


(function(jQuery) {
  var daysInWeek = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
  var shortMonthsInYear = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
  var longMonthsInYear = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
  var shortMonthsToNumber = [];
  shortMonthsToNumber['Jan'] = '01';
  shortMonthsToNumber['Feb'] = '02';
  shortMonthsToNumber['Mar'] = '03';
  shortMonthsToNumber['Apr'] = '04';
  shortMonthsToNumber['May'] = '05';
  shortMonthsToNumber['Jun'] = '06';
  shortMonthsToNumber['Jul'] = '07';
  shortMonthsToNumber['Aug'] = '08';
  shortMonthsToNumber['Sep'] = '09';
  shortMonthsToNumber['Oct'] = '10';
  shortMonthsToNumber['Nov'] = '11';
  shortMonthsToNumber['Dec'] = '12';

  jQuery.dateFormat = (function() {
    function strDay(value) {
      return daysInWeek[parseInt(value, 10)] || value;
    }

    function strMonth(value) {
      var monthArrayIndex = parseInt(value, 10) - 1;
      return shortMonthsInYear[monthArrayIndex] || value;
    }

    function strLongMonth(value) {
      var monthArrayIndex = parseInt(value, 10) - 1;
      return longMonthsInYear[monthArrayIndex] || value;
    }

    var parseMonth = function(value) {
      return shortMonthsToNumber[value] || value;
    };

    var parseTime = function(value) {
      var retValue = value;
      var millis = '';
      if(retValue.indexOf('.') !== -1) {
        var delimited = retValue.split('.');
        retValue = delimited[0];
        millis = delimited[1];
      }

      var values3 = retValue.split(':');

      if(values3.length === 3) {
        hour = values3[0];
        minute = values3[1];
        second = values3[2];
        return {
          time : retValue,
          hour : hour,
          minute : minute,
          second : second,
          millis : millis
        };
      } else {
        return {
          time : '',
          hour : '',
          minute : '',
          second : '',
          millis : ''
        };
      }
    };

    var padding = function(value, length) {
      var paddingCount = length - String(value).length;
      for(var i = 0; i < paddingCount; i++) {
        value = '0' + value;
      }
      return value;
    };

    var dateYYYYMMDDTimeRegexp = function() {
      return /\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}\.?\d{0,3}[Z\-+]?(\d{2}:?\d{2})?/;
    };

    return {
      date : function(value, format) {
        /*
         value = new java.util.Date()
         => 2009-12-18 10:54:50.546
        */
        try {
          var date = null;
          var year = null;
          var month = null;
          var dayOfMonth = null;
          var dayOfWeek = null;
          var time = null;
          if(typeof value == 'number') {
            return this.date(new Date(value), format);
          } else if(typeof value.getFullYear == 'function') {
            year = value.getFullYear();
            month = value.getMonth() + 1;
            dayOfMonth = value.getDate();
            dayOfWeek = value.getDay();
            time = parseTime(value.toTimeString());
          } else if(value.search(dateYYYYMMDDTimeRegexp()) != -1) {
            /* 2009-04-19T16:11:05+02:00 || 2009-04-19T16:11:05Z */
            var values = value.split(/[T\+-]/);
            year = values[0];
            month = values[1];
            dayOfMonth = values[2];
            time = parseTime(values[3].split('.')[0]);
            date = new Date(year, month - 1, dayOfMonth);
            dayOfWeek = date.getDay();
          } else {
            var values = value.split(' ');
            switch (values.length) {
              case 6:
                /* Wed Jan 13 10:43:41 CET 2010 */
                year = values[5];
                month = parseMonth(values[1]);
                dayOfMonth = values[2];
                time = parseTime(values[3]);
                date = new Date(year, month - 1, dayOfMonth);
                dayOfWeek = date.getDay();
                break;
              case 2:
                /* 2009-12-18 10:54:50.546 */
                var values2 = values[0].split('-');
                year = values2[0];
                month = values2[1];
                dayOfMonth = values2[2];
                time = parseTime(values[1]);
                date = new Date(year, month - 1, dayOfMonth);
                dayOfWeek = date.getDay();
                break;
              case 7:
                /* Tue Mar 01 2011 12:01:42 GMT-0800 (PST) */
              case 9:
                /* added by Larry, for Fri Apr 08 2011 00:00:00 GMT+0800 (China Standard Time) */
              case 10:
                /* added by Larry, for Fri Apr 08 2011 00:00:00 GMT+0200 (W. Europe Daylight Time) */
                year = values[3];
                month = parseMonth(values[1]);
                dayOfMonth = values[2];
                time = parseTime(values[4]);
                date = new Date(year, month - 1, dayOfMonth);
                dayOfWeek = date.getDay();
                break;
              case 1:
                /* added by Jonny, for 2012-02-07CET00:00:00 (Doctrine Entity -> Json Serializer) */
                var values2 = values[0].split('');
                year = values2[0] + values2[1] + values2[2] + values2[3];
                month = values2[5] + values2[6];
                dayOfMonth = values2[8] + values2[9];
                time = parseTime(values2[13] + values2[14] + values2[15]
                    + values2[16] + values2[17] + values2[18] + values2[19]
                    + values2[20])
                date = new Date(year, month - 1, dayOfMonth);
                dayOfWeek = date.getDay();
                break;
              default:
                return value;
            }
          }
          var pattern = '';
          var retValue = '';
          var unparsedRest = '';
          var inQuote = false;
          /* Issue 1 - variable scope issue in format.date (Thanks jakemonO) */
          for(var i = 0; i < format.length; i++) {
            var currentPattern = format.charAt(i);
            if (inQuote) {
              if (currentPattern == "'") {
                retValue += (pattern == '') ? "'" : pattern;
                pattern = '';
                inQuote = false;
              } else {
                pattern += currentPattern;
              }
              continue;
            }
            pattern += currentPattern;
            unparsedRest = '';
            switch (pattern) {
              case 'ddd':
                retValue += strDay(dayOfWeek);
                pattern = '';
                break;
              case 'dd':
                if(format.charAt(i + 1) == 'd') {
                  break;
                }
                retValue += padding(dayOfMonth, 2);
                pattern = '';
                break;
              case 'd':
                if(format.charAt(i + 1) == 'd') {
                  break;
                }
                retValue += parseInt(dayOfMonth, 10);
                pattern = '';
                break;
              case 'D':
                if(dayOfMonth == 1 || dayOfMonth == 21 || dayOfMonth == 31) {
                  dayOfMonth = parseInt(dayOfMonth, 10) + 'st';
                } else if(dayOfMonth == 2 || dayOfMonth == 22) {
                  dayOfMonth = parseInt(dayOfMonth, 10) + 'nd';
                } else if(dayOfMonth == 3 || dayOfMonth == 23) {
                  dayOfMonth = parseInt(dayOfMonth, 10) + 'rd';
                } else {
                  dayOfMonth = parseInt(dayOfMonth, 10) + 'th';
                }
                retValue += dayOfMonth;
                pattern = '';
                break;
              case 'MMMM':
                retValue += strLongMonth(month);
                pattern = '';
                break;
              case 'MMM':
                if(format.charAt(i + 1) === 'M') {
                  break;
                }
                retValue += strMonth(month);
                pattern = '';
                break;
              case 'MM':
                if(format.charAt(i + 1) == 'M') {
                  break;
                }
                retValue += padding(month, 2);
                pattern = '';
                break;
              case 'M':
                if(format.charAt(i + 1) == 'M') {
                  break;
                }
                retValue += parseInt(month, 10);
                pattern = '';
                break;
              case 'y':
              case 'yyy':
                if(format.charAt(i + 1) == 'y') {
                  break;
                }
                retValue += pattern;
                pattern = '';
                break;
              case 'yy':
                if(format.charAt(i + 1) == 'y' && format.charAt(i + 2) == 'y') {
                  break;
                }
                retValue += String(year).slice(-2);
                pattern = '';
                break;
              case 'yyyy':
                retValue += year;
                pattern = '';
                break;
              case 'HH':
                retValue += padding(time.hour, 2);
                pattern = '';
                break;
              case 'H':
                if(format.charAt(i + 1) == 'H') {
                  break;
                }
                retValue += parseInt(time.hour, 10);
                pattern = '';
                break;
              case 'hh':
                /* time.hour is '00' as string == is used instead of === */
                var hour = (time.hour == 0 ? 12 : time.hour < 13 ? time.hour
                    : time.hour - 12);
                retValue += padding(hour, 2);
                pattern = '';
                break;
              case 'h':
                if(format.charAt(i + 1) == 'h') {
                  break;
                }
                var hour = (time.hour == 0 ? 12 : time.hour < 13 ? time.hour
                    : time.hour - 12);
                retValue += parseInt(hour, 10);
                // Fixing issue https://github.com/phstc/jquery-dateFormat/issues/21
                // retValue = parseInt(retValue, 10);
                pattern = '';
                break;
              case 'mm':
                retValue += padding(time.minute, 2);
                pattern = '';
                break;
              case 'm':
                if(format.charAt(i + 1) == 'm') {
                  break;
                }
                retValue += time.minute;
                pattern = '';
                break;
              case 'ss':
                /* ensure only seconds are added to the return string */
                retValue += padding(time.second.substring(0, 2), 2);
                pattern = '';
                break;
              case 's':
                if(format.charAt(i + 1) == 's') {
                  break;
                }
                retValue += time.second;
                pattern = '';
                break;
              case 'S':
              case 'SS':
                if(format.charAt(i + 1) == 'S') {
                  break;
                }
                retValue += pattern;
                pattern = '';
                break;
              case 'SSS':
                retValue += time.millis.substring(0, 3);
                pattern = '';
                break;
              case 'a':
                retValue += time.hour >= 12 ? 'PM' : 'AM';
                pattern = '';
                break;
              case 'p':
                retValue += time.hour >= 12 ? 'p.m.' : 'a.m.';
                pattern = '';
                break;
              case "'":
                pattern = '';
                inQuote = true;
                break;
              default:
                retValue += currentPattern;
                pattern = '';
                break;
            }
          }
          retValue += unparsedRest;
          return retValue;
        } catch (e) {
          return value;
        }
      },
      /*
       * JavaScript Pretty Date
       * Copyright (c) 2011 John Resig (ejohn.org)
       * Licensed under the MIT and GPL licenses.
       *
       * Takes an ISO time and returns a string representing how long ago the date
       * represents
       *
       * ('2008-01-28T20:24:17Z') // => '2 hours ago'
       * ('2008-01-27T22:24:17Z') // => 'Yesterday'
       * ('2008-01-26T22:24:17Z') // => '2 days ago'
       * ('2008-01-14T22:24:17Z') // => '2 weeks ago'
       * ('2007-12-15T22:24:17Z') // => 'more than 5 weeks ago'
       *
       */
      prettyDate : function(time) {
        var date;
        var diff;
        var day_diff;
        if(typeof time === 'string' || typeof time === 'number') {
          date = new Date(time);
        }
        if(typeof time === 'object') {
          date = new Date(time.toString());
        }
        diff = (((new Date()).getTime() - date.getTime()) / 1000);
        day_diff = Math.floor(diff / 86400);
        if(isNaN(day_diff) || day_diff < 0) {
          return;
        }
        if(day_diff >= 31) {
          return 'more than 5 weeks ago';
        }
        return day_diff == 0
            && (diff < 60 && 'just now' || diff < 120 && '1 minute ago'
                || diff < 3600 && Math.floor(diff / 60) + ' minutes ago'
                || diff < 7200 && '1 hour ago' || diff < 86400
                && Math.floor(diff / 3600) + ' hours ago') || day_diff == 1
            && 'Yesterday' || day_diff < 7 && day_diff + ' days ago'
            || day_diff < 31 && Math.ceil(day_diff / 7) + ' weeks ago';
      },
      toBrowserTimeZone : function(value, format) {
        return this.date(new Date(value), format || 'MM/dd/yyyy HH:mm:ss');
      }
    };
  }());
}(jQuery));

$(document).ready(function(){
	list_daily_topics();
});

function today(){
	return $.dateFormat.date(new Date(), "yyyy-MM-dd");
}

function list_daily_topics(){
	$.ajax('http://www.davcai.com/ajax/tp_list?callback=?&date='+today(), {
		dataType: 'jsonp',
		success: function(data, status, jqXHR){
			render_daily_topics(data);
		},
		error: function(jqXHR, status, error){
			//alert(error);
		}
	});
}

function render_daily_topics(pageResults){
	var results = pageResults.results;
	var template =
	"<ul>{{#topics}}"+
	"<li>"+
		'<a href="/{{weibo.ownerId}}/{{weibo.id}}" target="_blank"><span class="show-cont">{{title}}</span></a>'+
		'<span class="show-time">{{create_fmt}}</span>'+
	'</li>{{/topics}}'+
	'</ul>';
	var segment = $.mustache(template, {
		topics: results.splice(0,5), 
		create_fmt: function(){
			var d = new Date(this.weibo.createTime);
			return $.dateFormat.date(d, 'yyyy-MM-dd');
		}
	});
	$('#daily_topics > ul').html(segment);
}
$(document).ready(function() {
	//给关注按钮绑定关注和取消关注事件
	$(".addFriendBtn").click(function (event) {
		followAndUnFollow( $(event.target));
	});
	
});

function followAndUnFollow(followBtn){
	var followFlag = "followFlag";
	var unFollowFlag = "unFollowFlag";
	
	var followString="＋加关注";
	var unFollowString="取消关注";
	if(followFlag == followBtn.attr("flag")) {
		var userId = followBtn.attr("_userId");
		$.post("/ajax_rltship_follow",{followIds:userId}, function(data , e) {
			if (data != null) {
				if (data.success) {
					followBtn.html(unFollowString);
					followBtn.attr("title","取消关注");
					followBtn.attr("flag",unFollowFlag);
				} else {
					alert(data.desc);
					if(data.resultCode == "notlogin"){
						window.location.href="http://www.davcai.com/";
					}
				}
			}
		}, 'json');
	} else {
		var userId = followBtn.attr("_userId");
		$.post("/ajax_rltship_unfollow",{unfollowIds:userId}, function(data , e) {
			if (data != null) {
				if (data.success) {
					followBtn.html(followString);
					followBtn.attr("title","加关注");
					followBtn.attr("flag",followFlag);
				} else {
					alert(data.desc);
				}
			}
		}, 'json');
	}
}
var userInfoBlockTpl =
	    '<div class="prompt-box" _usercard="_usercard" id="user_float_dialog_div_{{weiboUserId}}" style="position: absolute;" _userid="{{weiboUserId}}">'
	   +'	<div class="prompt-box-top">'
	   +'	<a href="/{{weiboUserId}}/profile" target="_blank"><img src="{{headImageURL}}" width="50px" height="50px" class="img_left"></a>'
	   +'	<div class="top-r">'
	   +'		<p class="name">{{nickName}}</p>'
	   +'		<p class="prompt-box-detail">'
	   +'			<a href="/{{weiboUserId}}/profile" target="_blank" class="zhutie">主贴</a><span>({{weiboCount}})</span><i>|</i>'
	   +'			<a href="/{{weiboUserId}}/profile/followings" target="_blank" class="guanzhu">关注</a><span>({{followingCount}})</span><i>|</i>'
	   +'			<a href="/{{weiboUserId}}/profile/followers" target="_blank" class="fensi">粉丝</a><span>({{followerCount}})</span>'
	   +'		</p>'
	   +'	    {{{followButton}}}'
	   +'	</div>'
	   +'	</div>'
	   +'	<div class="prompt-box-bottom">'
	   +'	  <div class="line">近7天战绩</div>'
	   +'	  <img src="http://www.davcai.com/images/real.png" class="real-img">'
	   +'	  <p>'
	   +'	  	       发<label class="fadan">{{realWeibo7Count}}</label>单，'
	   +'	  	       已开奖<label class="kaijiang">{{realWeibo7OpenCount}}</label>单，'
	   +'	  	       盈利<label class="zhongdan">{{realWeibo7GainCount}}</label>单，'
	   +'	  	       过单率<label class="guodanlv">{{guodanlv}}</label>'
	   +'	  </p>'
	   +'	</div>'
	   +'</div>';

function renderHeadImageInfoBlock(userId,event,type){
	var $block = $("#user_float_dialog_div_"+userId);
	var e = event.target;
	var x = e.offsetLeft,y = e.offsetTop;  
    while(e=e.offsetParent){
       x += e.offsetLeft;  
       y += e.offsetTop;
    }  
	var left =x - 13;
	var top = y + 50;
	if(type=="nickname"){
		var top = y+15;
	}
	if($block.length == 0){
		var url = '/' + userId + '/card';
		$.getJSON(url,function (weiboUserStatis){
			weiboUserStatis.guodanlv = weiboUserStatis.realWeibo7OpenCount == 0 ? "--" : parseInt(parseInt(weiboUserStatis.realWeibo7GainCount)/weiboUserStatis.realWeibo7OpenCount * 100) + "%";
			if(weiboUserStatis.self){
				weiboUserStatis.followButton ="";
			} else {
				weiboUserStatis.followButton = weiboUserStatis.beFollowed ?
						'<div class="attention addFriendBtn" flag="unFollowFlag" _userId="' +userId+'">取消关注</div>':
						'<div class="attention addFriendBtn" flag="followFlag" _userId="' +userId+'">+加关注</div>';
			}
			var html = $.mustache(userInfoBlockTpl,weiboUserStatis);
			$(document.body).append(html);
			$block = $("#user_float_dialog_div_"+userId);
			$block.mouseover(function (){ //浮框事件
				if(tHide){
					clearTimeout(tHide);
				}
				$block.show();
			}).mouseout(function (){
				tHide = setTimeout(function (){
					$("#user_float_dialog_div_"+userId).hide();
				},200);
			});
			$(".addFriendBtn",$block).click(function (){
				followAndUnFollow($(this));
			});
			$block.css("left",left + "px");
			$block.css("top",top + "px");
		});
	} else {
		$block.css("left",left + "px");
		$block.css("top",top + "px");
	}
	$("[_usercard]").hide();
	$block.show();
}
var tShow;
var tHide;
function bindMouseInHeadOrNicknameEvent ($context){
	var $target1 = $(".headpic",$context);
	$target1.mouseover(function (e){
		bindUserFloatCardMousecover($(this),e,"headpic");
	}).mouseout(function (e){
		bindUserFloatCardMouseout($(this),e,"headpic");
	});
	
	var $target2 = $('[_nickname_block="_nickname_block"]',$context);
	$target2.mouseover(function (e){
		bindUserFloatCardMousecover($(this),e,"nickname");
	}).mouseout(function (e){
		bindUserFloatCardMouseout($(this),e,"nickname");
	});
}
function bindUserFloatCardMousecover($this,e,type){
	if(tHide){
		clearTimeout(tHide);
	}
	tShow = setTimeout(function (){
		var userId = $this.attr("_userid");
		renderHeadImageInfoBlock(userId,e,type);
	},200);
}
function bindUserFloatCardMouseout($this,e,type){
	if(tShow){
		clearTimeout(tShow);
	}
	tHide = setTimeout(function (){
		var userId = $this.attr("_userid");
		$("#user_float_dialog_div_" + userId).hide();
	},200);
}
var matchInfoBlockTpl ='{{#data}}<div class="match-wrap" id="match_card_{{matchId}}" _homeTeamId="{{homeTeamId}}" _guestTeamId="{{guestTeamId}}" _matchcard="_matchcard" style="position: absolute;">'
  +'	<div class="match-wrap-top">'
  +'       <div class="match-wrap-top-left">'
  +'		<img src="{{homeTeamLogoUrl}}" class="img_left"/>'
  +'		<span class="name_left">{{homeTeamNameView}}</span>'
  +'      </div>'
  +'		      <div class="match-wrap-detail">'
  +'             <p class="score">'
  +'		 	      <span class="score_left">{{homeTeamScoreView}}</span>'
  +'		 	      <span class="vs">VS</span>'
  +'		 	      <span class="score_right">{{guestTeamScoreView}}</span>'
  +'              </p>'
  +'		 	      <p class="time"><label>{{matchTimeView}}</label></p>'
  +'		          <p class="first-half">上半场&nbsp;&nbsp;<label>25</label></p>'
  +'				  <p class="rest">中场休息</p>'
  +'				  <p class="second-half">上半场&nbsp;&nbsp;<label>45</label></p>'
  +'				  <p class="end">完场</p>'
  +'				  <p class="delay">延迟</p>'
  +'		 	      <p class="odds">{{odd3}}&nbsp&nbsp&nbsp{{odd1}}&nbsp&nbsp&nbsp{{odd0}}</p>'
  +'		     </div>'
  +'      <div class="match-wrap-top-right">'
  +'		<img src="{{guestTeamLogoUrl}}" class="img_right"/>'
  +'		<span class="name_right">{{guestTeamNameView}}</span>' 
  +'      </div>'
  +'	</div>'
  +'	<div class="match-wrap-bottom">'
  +'	  <div class="zan">'
  +'	  	  	<p class="shidan">实单：<span>{{realCount}}</span></p>'
  +'	  	  	<a href="javascript:void(0);" _id="favor_team_btn" isfavored="{{favorHomeTeam}}" teamId="{{homeTeamId}}" matchId="{{matchId}}" lotteryType="{{lotteryType}}">'
  +'		   		<p class="pop {{homeSelectedView}}"  _selectedColor="_selectedColor"><span class="hand"></span><label _id="favor_count_label">{{favorHomeTeamCount}}</label></p>'
  +'			</a>'
  +'	  </div>'
  +'	   <div class="zan">'
  +'	  	   <p class="shidan">推荐：<span>{{recCount}}</span></p>'
  +'	  	   <a href="javascript:void(0);" _id="favor_team_btn" isfavored="{{favorGuestTeam}}" teamId="{{guestTeamId}}" matchId="{{matchId}}" lotteryType="{{lotteryType}}">'
  +'		   		<p class="pop {{guestSelectedView}}" _selectedColor="_selectedColor"><span class="hand"></span><label _id="favor_count_label">{{favorGuestTeamCount}}</label></p>'
  +'		   </a>'
  +'	  </div>'
  +'	</div>'
  +'</div>{{/data}}';
var matchCardShowTimer;
var matchCardHideTimer;
function getFBMatchStatusName(matchStatus) {
		var status = "--";
		switch (matchStatus) {
		case 0:
			status = "未开";
			break;
		case 1:
			status = "上半场";
			break;
		case 2:
			status = "中场";
			break;
		case 3:
			status = "下半场";
			break;
		case -11:
			status = "待定";
			break;
		case -12:
			status = "腰斩";
			break;
		case -13:
			status = "中断";
			break;
		case -14:
			status = "推迟";
			break;
		case -1:
			status = "完场";
			break;
		}
		return status;
}
function getBBMatchStatusName(matchStatus) {
	var status = "--";
	switch (matchStatus) {
	case 0:
		status = "未开";
		break;
	case 1:
		status = "第一节";
		break;
	case 2:
		status = "第二节";
		break;
	case 3:
		status = "第三节";
		break;
	case 4:
		status = "第四节";
		break;
	case -1:
		status = "完场";
		break;
	case -2:
		status = "待定";
		break;
	case -3:
		status = "中断";
		break;
	case -4:
		status = "取消";
		break;
	case -5:
		status = "推迟";
		break;
	case 50:
		status = "中场";
		break;
	}
	return status;
}
function rendJCZQMatch(matchId,loteryType,left,top){
	$.getJSON("/ajax_match_float_card?matchId=" + matchId + "&lotteryType="+loteryType,function (data){
		var html = $.mustache(matchInfoBlockTpl,{
			data:data,
			matchTimeView: function (){
				if(this.matchStatus == 0 && this.matchTime){
					var time = this.matchTime.replace("T","  ");
					time = time.substring(0,time.length -3);
					return time;
				} else if(!this.matchTime){
					return ' ';
				} else {
					return getFBMatchStatusName(this.matchStatus);
				}
			},
			odd3:function (){
				if(this.odds){
					var odd = this.odds.split(",");
					return "胜：" + odd[0];
				} else {
					return "胜：--";
				}
			},
			odd1:function (){
				if(this.odds){
					var odd = this.odds.split(",");
					return "平：" + odd[1];
				} else {
					return "平：--";
				}
			},
			odd0:function (){
				if(this.odds){
					var odd = this.odds.split(",");
					return "负：" + odd[2];
				} else {
					return "负：--";
				}
			},
			homeTeamNameView:function (){
				return this.homeTeamName;
			},
			guestTeamNameView:function (){
				return this.guestTeamName;
			},
			homeTeamScoreView:function (){
				return this.homeTeamScore;
			},
			guestTeamScoreView:function (){
				return this.guestTeamScore;
			},
			homeSelectedView:function (){
				if(this.favorHomeTeam){
					return "selected";
				}
			},
			guestSelectedView:function (){
				if(this.favorGuestTeam){
					return "selected";
				}
			}
		});
		$(document.body).append(html);
		bindMatchCardAndShow(matchId,left,top);
	});
}
function rendJCLQMatch(matchId,loteryType,left,top){
	$.getJSON("/ajax_match_float_card?matchId=" + matchId + "&lotteryType="+loteryType,function (data){
		var html = $.mustache(matchInfoBlockTpl,{
			data:data,
			matchTimeView: function (){
				if(this.matchStatus == 0 && this.matchTime){
					var time = this.matchTime.replace("T","  ");
					time = time.substring(0,time.length -3);
					return time;
				} else if(!this.matchTime){
					return ' ';
				} else {
					return getBBMatchStatusName(this.matchStatus);
				}
			},
			odd3:function (){
				if(this.odds){
					var odd = this.odds.split(",");
					return "胜：" + odd[0];
				} else {
					return "胜：--";
				}
			},
			odd1:function (){
				return "";
			},
			odd0:function (){
				if(this.odds){
					var odd = this.odds.split(",");
					return "负：" + odd[1];
				} else {
					return "负：--";
				}
			},
			homeTeamNameView:function (){
				return this.homeTeam;
			},
			guestTeamNameView:function (){
				return this.guestTeam;
			},
			homeTeamScoreView:function (){
				if(this.matchStatus == 1){
					return this.homeOne;
				} else if(this.matchStatus == 2){
					return this.homeTwo;
				} else if(this.matchStatus == 3){
					return this.homeThree;
				} else if(this.matchStatus == 4){
					return this.homeFour;		
				} else if(this.matchStatus == -1){
					return this.homeScore;
				} else {
					return 0;
				} 
			},
			guestTeamScoreView:function (){
				if(this.matchStatus == 1){
					return this.guestOne;
				} else if(this.matchStatus == 2){
					return this.guestTwo;
				} else if(this.matchStatus == 3){
					return this.guestThree;
				} else if(this.matchStatus == 4){
					return this.guestFour;		
				} else if(this.matchStatus == -1){
					return this.guestScore;
				} else {
					return 0;
				}
			},homeSelectedView:function (){
				if(this.favorHomeTeam){
					return "selected";
				}
			},
			guestSelectedView:function (){
				if(this.favorGuestTeam){
					return "selected";
				}
			}
		});
		$(document.body).append(html);
		bindMatchCardAndShow(matchId,left,top);
	});
}

function bindMatchCardAndShow(matchId,left,top){
	var $block = $("#match_card_"+matchId);
	$block.mouseover(function (){ //浮框事件
		if(matchCardHideTimer){
			clearTimeout(matchCardHideTimer);
		}
		$(this).show();
	}).mouseout(function (){
		var $this = $(this);
		matchCardHideTimer = setTimeout(function (){
			$this.hide();
		},200);
	});
	bindFavorTeamEvent($("[_id='favor_team_btn']",$block));
	$block.css("left",left + "px");
	$block.css("top",top + "px");
	$("[_matchcard]").hide();
	$block.show();
}
function bindFavorTeamEvent($btn){
	$btn.click(function (e){
		var $this = $(this);
		$this.unbind("click");
		var data = {
			lotteryType:$this.attr("lotteryType"),
			teamId:$this.attr("teamId"),
			matchId:$this.attr("matchId")
		};
		if($this.attr("isfavored") == "true"){
			sendUnFavorMatchTeam(data,$this);
		} else {
			sendFavorMatchTeam(data,$this);
		}
	});
}

function sendFavorMatchTeam(data,$this){
	$.ajax({
		type:"post",
		dataType:"json",
		url : "/ajax_like_team.do",
		data:data,
		success : function(result) {
			if(result && result.success == true) {
				$.msgbox('支持成功', {anchor:$this,success:true});
				var $countLabel = $("[_id='favor_count_label']",$this);
				var $color = $("[ _selectedColor='_selectedColor']",$this);
				var count = $countLabel.text();
				count = parseInt(count) + 1;
				$countLabel.html(count);
				$color.addClass("selected");
				$this.attr("isfavored","true");
			} else {
				$.msgbox('支持失败：' + result.data, {anchor:$this,success:false});
			}
			bindFavorTeamEvent($this);
		}
	});
}
function sendUnFavorMatchTeam(data,$this){
	$.ajax({
		type:"post",
		dataType:"json",
		url : "/ajax_unlike_team",
		data:data,
		success : function(result) {
			if(result && result.success == true) {
				$.msgbox('已取消支持', {anchor:$this,success:true});
				var $countLabel = $("[_id='favor_count_label']",$this);
				var $color = $("[ _selectedColor='_selectedColor']",$this);
				var count = $countLabel.text();
				count = parseInt(count) - 1;
				$countLabel.html(count <= 0 ? 0:count);
				$color.removeClass("selected");
				$this.attr("isfavored","false");
			} else {
				$.msgbox('取消支持失败：' + result.data, {anchor:$this,success:false});
			}
			bindFavorTeamEvent($this);
		}
	});
}
function rendMatchCard(matchId,loteryType,event,iframeid){
	var $block = $("#match_card_"+matchId);
	var iframe = $("#"+ iframeid);
	var e = event.target;
	var result =  getElementPosition(e,iframe[0]);
	if($block.length == 0){
		if(loteryType == "JCZQ" || loteryType == "BJDC"){
			rendJCZQMatch(matchId,loteryType,result.x,result.y);
		} else if(loteryType == "JCLQ"){
			rendJCLQMatch(matchId,loteryType,result.x,result.y);
		} else if(loteryType == "CTZC"){
			
		} else {
			
		}
	} else {
		$block.css("left",result.x + "px");
		$block.css("top",result.y + "px");
		$("[_matchcard]").hide();
		$block.show();
	}
}

function bindMouseInMatch$StringEvent($context){
	$matchs = $("a[_matchFloatCardType]",$context);
	$matchs.each(function (index){
		$(this).mouseover(function (e){
			var $this = $(this);
			if(matchCardHideTimer){
				clearTimeout(matchCardHideTimer);
			}
			matchCardShowTimer = setTimeout(function (){
				var matchId = $this.attr("_matchId");
				var loteryType = $this.attr("_matchFloatCardType");
				rendMatchCard(matchId,loteryType,e,$this.attr("iframeid"));
			},200);
		}).mouseout(function (e){
			var $this = $(this);
			if(matchCardShowTimer){
				clearTimeout(matchCardShowTimer);
			}
			matchCardHideTimer = setTimeout(function (){
				var matchId = $this.attr("_matchId");
				$("#match_card_"+matchId).hide();
			},200);
		});
	});
}

function getElementPosition(el,iframe) { 
	var obj=el,offset=new Object(),x=0,y=0; 
	while(obj&&obj.tagName != "BODY") { 
		x+=obj.offsetLeft; 
		y+=obj.offsetTop; 
		obj=obj.offsetParent;
	}
	obj = iframe;
	while(obj&&obj.tagName != "BODY") { 
		x+=obj.offsetLeft; 
		y+=obj.offsetTop; 
		obj=obj.offsetParent;
	}
	offset.x=x-320;
	offset.y=y+15;
	return offset; 
}
$(function() {
	var userTmp = 
		'<div id="gallery">'
	   +'    <span class="image-wrapper">'
	   +'        <a href="http://www.davcai.com/{0}/profile" title="{1}">'
	   +'       <img alt="{1}" src="{2}"  height="100" width="100"></a>'
	   +'   </span>'
	   +'   <div id="caption" class="caption-container">'
	   +'       <div id="image-frame">'
	   +'           <span class="point"><span class="sky"></span></span>'
	   +'           <div class="image-content">'
	   +'               <h2>{1}</h2>'
	   +'               <div style="font-size: 12px;line-height: 24px;color: #888;" class="item_desc">'
	   +'                   {3}'
	   +'               </div>'
	   +'           </div>'
	   +'       </div>'
	   +'   </div>'
	   +'</div>'
	var detailLi = $("#show-recommend-user-detail");
	var index = 0;
	function showRcmdUserDetail(){
		index ++;
		if(index > 11){
			index = 0;
		}
		var li = $("li",$("#show-recommend-users"))[index];
		var li = $(li);
		var weiboUserId = li.attr("weiboUserId");
		var nickName = li.attr("nickName");
		var headImageURL = li.attr("headImageURL");
		var individualResume = li.attr("resume");
		var html = $.format(userTmp,weiboUserId,nickName,headImageURL,individualResume);
		detailLi.html(html);
		setTimeout(function (){
			$("#gallery").fadeOut(500,showRcmdUserDetail);
		},3000);
	}
	showRcmdUserDetail();
});


function login() {
	var username = $.trim($("#username").val());
	var password = $.trim($("#password").val());
	var rememberMe = $("#remember_me").attr("checked");
	if(username == null || username == ""){
		alert("请输入用户名");
		$("#username").focus();
		return false;
	}
	if(password == null || password == ""){
		alert("请输入密码");
		$("#password").focus();
		return false;
	}
	if(rememberMe){
		$.cookie('username', username,{ expires: 90 });
	} else {
		$.cookie('username', null);
	}
	$("#loginForm").attr("action",
			"http://login.davcai.com/login.do");
	$("#loginForm").attr("method", "post");
	$("#loginForm").submit();
}

function loginBySina(redirect) {
	var url = "https://api.weibo.com/oauth2/authorize?client_id=525200372&response_type=code&scope=all&redirect_uri=" + redirect;
	window.location.href = url;
}
function loginByAlipay(redirect) {
	var url = "https://openauth.alipay.com/oauth2/authorize.htm?client_id=2014090100010261&redirect_uri=" + redirect;
	window.location.href = url;
}

function loginByQQ(redirect) {
	var qq = "https://graph.qq.com/oauth2.0/authorize?response_type=token&client_id=101150376&scope=get_user_info,get_info,add_t,add_pic_t,get_other_info,get_fanslist,get_idollist&redirect_uri=" + redirect;
	window.location.href = qq;
}

function loginByWeixin(){
	window.location.href='https://open.weixin.qq.com/connect/qrconnect?appid=wx8a684cf1f5b6a359&scope=snsapi_login&redirect_uri=http://www.davcai.com/weixin_login&state=1418271445397&login_type=jssdk&style=white';
}

function toRegistPage(referer,failReturnURL) {
	var registUrl = "http://login.davcai.com/regist.do";
	window.location.href = registUrl + "?referer=" + referer + "&failReturnURL=" + failReturnURL;
}

function logout(referer){
	window.location.href= "http://login.davcai.com/sso/logout.do?referer=" + referer;
}

function addNickname(){
 	if(jQuery("#nickname").val().length === 0 ) {
        alert("请给自己取一个昵称吧");
		return false;
    } 
    document.getElementById("add_nickname_form").submit();
}
/*! jQuery UI - v1.10.4 - 2014-06-15
* http://jqueryui.com
* Includes: jquery.ui.core.js, jquery.ui.datepicker.js
* Copyright 2014 jQuery Foundation and other contributors; Licensed MIT */

(function(e,t){function i(t,i){var s,a,o,r=t.nodeName.toLowerCase();return"area"===r?(s=t.parentNode,a=s.name,t.href&&a&&"map"===s.nodeName.toLowerCase()?(o=e("img[usemap=#"+a+"]")[0],!!o&&n(o)):!1):(/input|select|textarea|button|object/.test(r)?!t.disabled:"a"===r?t.href||i:i)&&n(t)}function n(t){return e.expr.filters.visible(t)&&!e(t).parents().addBack().filter(function(){return"hidden"===e.css(this,"visibility")}).length}var s=0,a=/^ui-id-\d+$/;e.ui=e.ui||{},e.extend(e.ui,{version:"1.10.4",keyCode:{BACKSPACE:8,COMMA:188,DELETE:46,DOWN:40,END:35,ENTER:13,ESCAPE:27,HOME:36,LEFT:37,NUMPAD_ADD:107,NUMPAD_DECIMAL:110,NUMPAD_DIVIDE:111,NUMPAD_ENTER:108,NUMPAD_MULTIPLY:106,NUMPAD_SUBTRACT:109,PAGE_DOWN:34,PAGE_UP:33,PERIOD:190,RIGHT:39,SPACE:32,TAB:9,UP:38}}),e.fn.extend({focus:function(t){return function(i,n){return"number"==typeof i?this.each(function(){var t=this;setTimeout(function(){e(t).focus(),n&&n.call(t)},i)}):t.apply(this,arguments)}}(e.fn.focus),scrollParent:function(){var t;return t=e.ui.ie&&/(static|relative)/.test(this.css("position"))||/absolute/.test(this.css("position"))?this.parents().filter(function(){return/(relative|absolute|fixed)/.test(e.css(this,"position"))&&/(auto|scroll)/.test(e.css(this,"overflow")+e.css(this,"overflow-y")+e.css(this,"overflow-x"))}).eq(0):this.parents().filter(function(){return/(auto|scroll)/.test(e.css(this,"overflow")+e.css(this,"overflow-y")+e.css(this,"overflow-x"))}).eq(0),/fixed/.test(this.css("position"))||!t.length?e(document):t},zIndex:function(i){if(i!==t)return this.css("zIndex",i);if(this.length)for(var n,s,a=e(this[0]);a.length&&a[0]!==document;){if(n=a.css("position"),("absolute"===n||"relative"===n||"fixed"===n)&&(s=parseInt(a.css("zIndex"),10),!isNaN(s)&&0!==s))return s;a=a.parent()}return 0},uniqueId:function(){return this.each(function(){this.id||(this.id="ui-id-"+ ++s)})},removeUniqueId:function(){return this.each(function(){a.test(this.id)&&e(this).removeAttr("id")})}}),e.extend(e.expr[":"],{data:e.expr.createPseudo?e.expr.createPseudo(function(t){return function(i){return!!e.data(i,t)}}):function(t,i,n){return!!e.data(t,n[3])},focusable:function(t){return i(t,!isNaN(e.attr(t,"tabindex")))},tabbable:function(t){var n=e.attr(t,"tabindex"),s=isNaN(n);return(s||n>=0)&&i(t,!s)}}),e("<a>").outerWidth(1).jquery||e.each(["Width","Height"],function(i,n){function s(t,i,n,s){return e.each(a,function(){i-=parseFloat(e.css(t,"padding"+this))||0,n&&(i-=parseFloat(e.css(t,"border"+this+"Width"))||0),s&&(i-=parseFloat(e.css(t,"margin"+this))||0)}),i}var a="Width"===n?["Left","Right"]:["Top","Bottom"],o=n.toLowerCase(),r={innerWidth:e.fn.innerWidth,innerHeight:e.fn.innerHeight,outerWidth:e.fn.outerWidth,outerHeight:e.fn.outerHeight};e.fn["inner"+n]=function(i){return i===t?r["inner"+n].call(this):this.each(function(){e(this).css(o,s(this,i)+"px")})},e.fn["outer"+n]=function(t,i){return"number"!=typeof t?r["outer"+n].call(this,t):this.each(function(){e(this).css(o,s(this,t,!0,i)+"px")})}}),e.fn.addBack||(e.fn.addBack=function(e){return this.add(null==e?this.prevObject:this.prevObject.filter(e))}),e("<a>").data("a-b","a").removeData("a-b").data("a-b")&&(e.fn.removeData=function(t){return function(i){return arguments.length?t.call(this,e.camelCase(i)):t.call(this)}}(e.fn.removeData)),e.ui.ie=!!/msie [\w.]+/.exec(navigator.userAgent.toLowerCase()),e.support.selectstart="onselectstart"in document.createElement("div"),e.fn.extend({disableSelection:function(){return this.bind((e.support.selectstart?"selectstart":"mousedown")+".ui-disableSelection",function(e){e.preventDefault()})},enableSelection:function(){return this.unbind(".ui-disableSelection")}}),e.extend(e.ui,{plugin:{add:function(t,i,n){var s,a=e.ui[t].prototype;for(s in n)a.plugins[s]=a.plugins[s]||[],a.plugins[s].push([i,n[s]])},call:function(e,t,i){var n,s=e.plugins[t];if(s&&e.element[0].parentNode&&11!==e.element[0].parentNode.nodeType)for(n=0;s.length>n;n++)e.options[s[n][0]]&&s[n][1].apply(e.element,i)}},hasScroll:function(t,i){if("hidden"===e(t).css("overflow"))return!1;var n=i&&"left"===i?"scrollLeft":"scrollTop",s=!1;return t[n]>0?!0:(t[n]=1,s=t[n]>0,t[n]=0,s)}})})(jQuery);(function(e,t){function i(){this._curInst=null,this._keyEvent=!1,this._disabledInputs=[],this._datepickerShowing=!1,this._inDialog=!1,this._mainDivId="ui-datepicker-div",this._inlineClass="ui-datepicker-inline",this._appendClass="ui-datepicker-append",this._triggerClass="ui-datepicker-trigger",this._dialogClass="ui-datepicker-dialog",this._disableClass="ui-datepicker-disabled",this._unselectableClass="ui-datepicker-unselectable",this._currentClass="ui-datepicker-current-day",this._dayOverClass="ui-datepicker-days-cell-over",this.regional=[],this.regional[""]={closeText:"Done",prevText:"Prev",nextText:"Next",currentText:"Today",monthNames:["January","February","March","April","May","June","July","August","September","October","November","December"],monthNamesShort:["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"],dayNames:["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"],dayNamesShort:["Sun","Mon","Tue","Wed","Thu","Fri","Sat"],dayNamesMin:["Su","Mo","Tu","We","Th","Fr","Sa"],weekHeader:"Wk",dateFormat:"mm/dd/yy",firstDay:0,isRTL:!1,showMonthAfterYear:!1,yearSuffix:""},this._defaults={showOn:"focus",showAnim:"fadeIn",showOptions:{},defaultDate:null,appendText:"",buttonText:"...",buttonImage:"",buttonImageOnly:!1,hideIfNoPrevNext:!1,navigationAsDateFormat:!1,gotoCurrent:!1,changeMonth:!1,changeYear:!1,yearRange:"c-10:c+10",showOtherMonths:!1,selectOtherMonths:!1,showWeek:!1,calculateWeek:this.iso8601Week,shortYearCutoff:"+10",minDate:null,maxDate:null,duration:"fast",beforeShowDay:null,beforeShow:null,onSelect:null,onChangeMonthYear:null,onClose:null,numberOfMonths:1,showCurrentAtPos:0,stepMonths:1,stepBigMonths:12,altField:"",altFormat:"",constrainInput:!0,showButtonPanel:!1,autoSize:!1,disabled:!1},e.extend(this._defaults,this.regional[""]),this.dpDiv=a(e("<div id='"+this._mainDivId+"' class='ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all'></div>"))}function a(t){var i="button, .ui-datepicker-prev, .ui-datepicker-next, .ui-datepicker-calendar td a";return t.delegate(i,"mouseout",function(){e(this).removeClass("ui-state-hover"),-1!==this.className.indexOf("ui-datepicker-prev")&&e(this).removeClass("ui-datepicker-prev-hover"),-1!==this.className.indexOf("ui-datepicker-next")&&e(this).removeClass("ui-datepicker-next-hover")}).delegate(i,"mouseover",function(){e.datepicker._isDisabledDatepicker(n.inline?t.parent()[0]:n.input[0])||(e(this).parents(".ui-datepicker-calendar").find("a").removeClass("ui-state-hover"),e(this).addClass("ui-state-hover"),-1!==this.className.indexOf("ui-datepicker-prev")&&e(this).addClass("ui-datepicker-prev-hover"),-1!==this.className.indexOf("ui-datepicker-next")&&e(this).addClass("ui-datepicker-next-hover"))})}function s(t,i){e.extend(t,i);for(var a in i)null==i[a]&&(t[a]=i[a]);return t}e.extend(e.ui,{datepicker:{version:"1.10.4"}});var n,r="datepicker";e.extend(i.prototype,{markerClassName:"hasDatepicker",maxRows:4,_widgetDatepicker:function(){return this.dpDiv},setDefaults:function(e){return s(this._defaults,e||{}),this},_attachDatepicker:function(t,i){var a,s,n;a=t.nodeName.toLowerCase(),s="div"===a||"span"===a,t.id||(this.uuid+=1,t.id="dp"+this.uuid),n=this._newInst(e(t),s),n.settings=e.extend({},i||{}),"input"===a?this._connectDatepicker(t,n):s&&this._inlineDatepicker(t,n)},_newInst:function(t,i){var s=t[0].id.replace(/([^A-Za-z0-9_\-])/g,"\\\\$1");return{id:s,input:t,selectedDay:0,selectedMonth:0,selectedYear:0,drawMonth:0,drawYear:0,inline:i,dpDiv:i?a(e("<div class='"+this._inlineClass+" ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all'></div>")):this.dpDiv}},_connectDatepicker:function(t,i){var a=e(t);i.append=e([]),i.trigger=e([]),a.hasClass(this.markerClassName)||(this._attachments(a,i),a.addClass(this.markerClassName).keydown(this._doKeyDown).keypress(this._doKeyPress).keyup(this._doKeyUp),this._autoSize(i),e.data(t,r,i),i.settings.disabled&&this._disableDatepicker(t))},_attachments:function(t,i){var a,s,n,r=this._get(i,"appendText"),o=this._get(i,"isRTL");i.append&&i.append.remove(),r&&(i.append=e("<span class='"+this._appendClass+"'>"+r+"</span>"),t[o?"before":"after"](i.append)),t.unbind("focus",this._showDatepicker),i.trigger&&i.trigger.remove(),a=this._get(i,"showOn"),("focus"===a||"both"===a)&&t.focus(this._showDatepicker),("button"===a||"both"===a)&&(s=this._get(i,"buttonText"),n=this._get(i,"buttonImage"),i.trigger=e(this._get(i,"buttonImageOnly")?e("<img/>").addClass(this._triggerClass).attr({src:n,alt:s,title:s}):e("<button type='button'></button>").addClass(this._triggerClass).html(n?e("<img/>").attr({src:n,alt:s,title:s}):s)),t[o?"before":"after"](i.trigger),i.trigger.click(function(){return e.datepicker._datepickerShowing&&e.datepicker._lastInput===t[0]?e.datepicker._hideDatepicker():e.datepicker._datepickerShowing&&e.datepicker._lastInput!==t[0]?(e.datepicker._hideDatepicker(),e.datepicker._showDatepicker(t[0])):e.datepicker._showDatepicker(t[0]),!1}))},_autoSize:function(e){if(this._get(e,"autoSize")&&!e.inline){var t,i,a,s,n=new Date(2009,11,20),r=this._get(e,"dateFormat");r.match(/[DM]/)&&(t=function(e){for(i=0,a=0,s=0;e.length>s;s++)e[s].length>i&&(i=e[s].length,a=s);return a},n.setMonth(t(this._get(e,r.match(/MM/)?"monthNames":"monthNamesShort"))),n.setDate(t(this._get(e,r.match(/DD/)?"dayNames":"dayNamesShort"))+20-n.getDay())),e.input.attr("size",this._formatDate(e,n).length)}},_inlineDatepicker:function(t,i){var a=e(t);a.hasClass(this.markerClassName)||(a.addClass(this.markerClassName).append(i.dpDiv),e.data(t,r,i),this._setDate(i,this._getDefaultDate(i),!0),this._updateDatepicker(i),this._updateAlternate(i),i.settings.disabled&&this._disableDatepicker(t),i.dpDiv.css("display","block"))},_dialogDatepicker:function(t,i,a,n,o){var u,c,h,l,d,p=this._dialogInst;return p||(this.uuid+=1,u="dp"+this.uuid,this._dialogInput=e("<input type='text' id='"+u+"' style='position: absolute; top: -100px; width: 0px;'/>"),this._dialogInput.keydown(this._doKeyDown),e("body").append(this._dialogInput),p=this._dialogInst=this._newInst(this._dialogInput,!1),p.settings={},e.data(this._dialogInput[0],r,p)),s(p.settings,n||{}),i=i&&i.constructor===Date?this._formatDate(p,i):i,this._dialogInput.val(i),this._pos=o?o.length?o:[o.pageX,o.pageY]:null,this._pos||(c=document.documentElement.clientWidth,h=document.documentElement.clientHeight,l=document.documentElement.scrollLeft||document.body.scrollLeft,d=document.documentElement.scrollTop||document.body.scrollTop,this._pos=[c/2-100+l,h/2-150+d]),this._dialogInput.css("left",this._pos[0]+20+"px").css("top",this._pos[1]+"px"),p.settings.onSelect=a,this._inDialog=!0,this.dpDiv.addClass(this._dialogClass),this._showDatepicker(this._dialogInput[0]),e.blockUI&&e.blockUI(this.dpDiv),e.data(this._dialogInput[0],r,p),this},_destroyDatepicker:function(t){var i,a=e(t),s=e.data(t,r);a.hasClass(this.markerClassName)&&(i=t.nodeName.toLowerCase(),e.removeData(t,r),"input"===i?(s.append.remove(),s.trigger.remove(),a.removeClass(this.markerClassName).unbind("focus",this._showDatepicker).unbind("keydown",this._doKeyDown).unbind("keypress",this._doKeyPress).unbind("keyup",this._doKeyUp)):("div"===i||"span"===i)&&a.removeClass(this.markerClassName).empty())},_enableDatepicker:function(t){var i,a,s=e(t),n=e.data(t,r);s.hasClass(this.markerClassName)&&(i=t.nodeName.toLowerCase(),"input"===i?(t.disabled=!1,n.trigger.filter("button").each(function(){this.disabled=!1}).end().filter("img").css({opacity:"1.0",cursor:""})):("div"===i||"span"===i)&&(a=s.children("."+this._inlineClass),a.children().removeClass("ui-state-disabled"),a.find("select.ui-datepicker-month, select.ui-datepicker-year").prop("disabled",!1)),this._disabledInputs=e.map(this._disabledInputs,function(e){return e===t?null:e}))},_disableDatepicker:function(t){var i,a,s=e(t),n=e.data(t,r);s.hasClass(this.markerClassName)&&(i=t.nodeName.toLowerCase(),"input"===i?(t.disabled=!0,n.trigger.filter("button").each(function(){this.disabled=!0}).end().filter("img").css({opacity:"0.5",cursor:"default"})):("div"===i||"span"===i)&&(a=s.children("."+this._inlineClass),a.children().addClass("ui-state-disabled"),a.find("select.ui-datepicker-month, select.ui-datepicker-year").prop("disabled",!0)),this._disabledInputs=e.map(this._disabledInputs,function(e){return e===t?null:e}),this._disabledInputs[this._disabledInputs.length]=t)},_isDisabledDatepicker:function(e){if(!e)return!1;for(var t=0;this._disabledInputs.length>t;t++)if(this._disabledInputs[t]===e)return!0;return!1},_getInst:function(t){try{return e.data(t,r)}catch(i){throw"Missing instance data for this datepicker"}},_optionDatepicker:function(i,a,n){var r,o,u,c,h=this._getInst(i);return 2===arguments.length&&"string"==typeof a?"defaults"===a?e.extend({},e.datepicker._defaults):h?"all"===a?e.extend({},h.settings):this._get(h,a):null:(r=a||{},"string"==typeof a&&(r={},r[a]=n),h&&(this._curInst===h&&this._hideDatepicker(),o=this._getDateDatepicker(i,!0),u=this._getMinMaxDate(h,"min"),c=this._getMinMaxDate(h,"max"),s(h.settings,r),null!==u&&r.dateFormat!==t&&r.minDate===t&&(h.settings.minDate=this._formatDate(h,u)),null!==c&&r.dateFormat!==t&&r.maxDate===t&&(h.settings.maxDate=this._formatDate(h,c)),"disabled"in r&&(r.disabled?this._disableDatepicker(i):this._enableDatepicker(i)),this._attachments(e(i),h),this._autoSize(h),this._setDate(h,o),this._updateAlternate(h),this._updateDatepicker(h)),t)},_changeDatepicker:function(e,t,i){this._optionDatepicker(e,t,i)},_refreshDatepicker:function(e){var t=this._getInst(e);t&&this._updateDatepicker(t)},_setDateDatepicker:function(e,t){var i=this._getInst(e);i&&(this._setDate(i,t),this._updateDatepicker(i),this._updateAlternate(i))},_getDateDatepicker:function(e,t){var i=this._getInst(e);return i&&!i.inline&&this._setDateFromField(i,t),i?this._getDate(i):null},_doKeyDown:function(t){var i,a,s,n=e.datepicker._getInst(t.target),r=!0,o=n.dpDiv.is(".ui-datepicker-rtl");if(n._keyEvent=!0,e.datepicker._datepickerShowing)switch(t.keyCode){case 9:e.datepicker._hideDatepicker(),r=!1;break;case 13:return s=e("td."+e.datepicker._dayOverClass+":not(."+e.datepicker._currentClass+")",n.dpDiv),s[0]&&e.datepicker._selectDay(t.target,n.selectedMonth,n.selectedYear,s[0]),i=e.datepicker._get(n,"onSelect"),i?(a=e.datepicker._formatDate(n),i.apply(n.input?n.input[0]:null,[a,n])):e.datepicker._hideDatepicker(),!1;case 27:e.datepicker._hideDatepicker();break;case 33:e.datepicker._adjustDate(t.target,t.ctrlKey?-e.datepicker._get(n,"stepBigMonths"):-e.datepicker._get(n,"stepMonths"),"M");break;case 34:e.datepicker._adjustDate(t.target,t.ctrlKey?+e.datepicker._get(n,"stepBigMonths"):+e.datepicker._get(n,"stepMonths"),"M");break;case 35:(t.ctrlKey||t.metaKey)&&e.datepicker._clearDate(t.target),r=t.ctrlKey||t.metaKey;break;case 36:(t.ctrlKey||t.metaKey)&&e.datepicker._gotoToday(t.target),r=t.ctrlKey||t.metaKey;break;case 37:(t.ctrlKey||t.metaKey)&&e.datepicker._adjustDate(t.target,o?1:-1,"D"),r=t.ctrlKey||t.metaKey,t.originalEvent.altKey&&e.datepicker._adjustDate(t.target,t.ctrlKey?-e.datepicker._get(n,"stepBigMonths"):-e.datepicker._get(n,"stepMonths"),"M");break;case 38:(t.ctrlKey||t.metaKey)&&e.datepicker._adjustDate(t.target,-7,"D"),r=t.ctrlKey||t.metaKey;break;case 39:(t.ctrlKey||t.metaKey)&&e.datepicker._adjustDate(t.target,o?-1:1,"D"),r=t.ctrlKey||t.metaKey,t.originalEvent.altKey&&e.datepicker._adjustDate(t.target,t.ctrlKey?+e.datepicker._get(n,"stepBigMonths"):+e.datepicker._get(n,"stepMonths"),"M");break;case 40:(t.ctrlKey||t.metaKey)&&e.datepicker._adjustDate(t.target,7,"D"),r=t.ctrlKey||t.metaKey;break;default:r=!1}else 36===t.keyCode&&t.ctrlKey?e.datepicker._showDatepicker(this):r=!1;r&&(t.preventDefault(),t.stopPropagation())},_doKeyPress:function(i){var a,s,n=e.datepicker._getInst(i.target);return e.datepicker._get(n,"constrainInput")?(a=e.datepicker._possibleChars(e.datepicker._get(n,"dateFormat")),s=String.fromCharCode(null==i.charCode?i.keyCode:i.charCode),i.ctrlKey||i.metaKey||" ">s||!a||a.indexOf(s)>-1):t},_doKeyUp:function(t){var i,a=e.datepicker._getInst(t.target);if(a.input.val()!==a.lastVal)try{i=e.datepicker.parseDate(e.datepicker._get(a,"dateFormat"),a.input?a.input.val():null,e.datepicker._getFormatConfig(a)),i&&(e.datepicker._setDateFromField(a),e.datepicker._updateAlternate(a),e.datepicker._updateDatepicker(a))}catch(s){}return!0},_showDatepicker:function(t){if(t=t.target||t,"input"!==t.nodeName.toLowerCase()&&(t=e("input",t.parentNode)[0]),!e.datepicker._isDisabledDatepicker(t)&&e.datepicker._lastInput!==t){var i,a,n,r,o,u,c;i=e.datepicker._getInst(t),e.datepicker._curInst&&e.datepicker._curInst!==i&&(e.datepicker._curInst.dpDiv.stop(!0,!0),i&&e.datepicker._datepickerShowing&&e.datepicker._hideDatepicker(e.datepicker._curInst.input[0])),a=e.datepicker._get(i,"beforeShow"),n=a?a.apply(t,[t,i]):{},n!==!1&&(s(i.settings,n),i.lastVal=null,e.datepicker._lastInput=t,e.datepicker._setDateFromField(i),e.datepicker._inDialog&&(t.value=""),e.datepicker._pos||(e.datepicker._pos=e.datepicker._findPos(t),e.datepicker._pos[1]+=t.offsetHeight),r=!1,e(t).parents().each(function(){return r|="fixed"===e(this).css("position"),!r}),o={left:e.datepicker._pos[0],top:e.datepicker._pos[1]},e.datepicker._pos=null,i.dpDiv.empty(),i.dpDiv.css({position:"absolute",display:"block",top:"-1000px"}),e.datepicker._updateDatepicker(i),o=e.datepicker._checkOffset(i,o,r),i.dpDiv.css({position:e.datepicker._inDialog&&e.blockUI?"static":r?"fixed":"absolute",display:"none",left:o.left+"px",top:o.top+"px"}),i.inline||(u=e.datepicker._get(i,"showAnim"),c=e.datepicker._get(i,"duration"),i.dpDiv.zIndex(e(t).zIndex()+1),e.datepicker._datepickerShowing=!0,e.effects&&e.effects.effect[u]?i.dpDiv.show(u,e.datepicker._get(i,"showOptions"),c):i.dpDiv[u||"show"](u?c:null),e.datepicker._shouldFocusInput(i)&&i.input.focus(),e.datepicker._curInst=i))}},_updateDatepicker:function(t){this.maxRows=4,n=t,t.dpDiv.empty().append(this._generateHTML(t)),this._attachHandlers(t),t.dpDiv.find("."+this._dayOverClass+" a").mouseover();var i,a=this._getNumberOfMonths(t),s=a[1],r=17;t.dpDiv.removeClass("ui-datepicker-multi-2 ui-datepicker-multi-3 ui-datepicker-multi-4").width(""),s>1&&t.dpDiv.addClass("ui-datepicker-multi-"+s).css("width",r*s+"em"),t.dpDiv[(1!==a[0]||1!==a[1]?"add":"remove")+"Class"]("ui-datepicker-multi"),t.dpDiv[(this._get(t,"isRTL")?"add":"remove")+"Class"]("ui-datepicker-rtl"),t===e.datepicker._curInst&&e.datepicker._datepickerShowing&&e.datepicker._shouldFocusInput(t)&&t.input.focus(),t.yearshtml&&(i=t.yearshtml,setTimeout(function(){i===t.yearshtml&&t.yearshtml&&t.dpDiv.find("select.ui-datepicker-year:first").replaceWith(t.yearshtml),i=t.yearshtml=null},0))},_shouldFocusInput:function(e){return e.input&&e.input.is(":visible")&&!e.input.is(":disabled")&&!e.input.is(":focus")},_checkOffset:function(t,i,a){var s=t.dpDiv.outerWidth(),n=t.dpDiv.outerHeight(),r=t.input?t.input.outerWidth():0,o=t.input?t.input.outerHeight():0,u=document.documentElement.clientWidth+(a?0:e(document).scrollLeft()),c=document.documentElement.clientHeight+(a?0:e(document).scrollTop());return i.left-=this._get(t,"isRTL")?s-r:0,i.left-=a&&i.left===t.input.offset().left?e(document).scrollLeft():0,i.top-=a&&i.top===t.input.offset().top+o?e(document).scrollTop():0,i.left-=Math.min(i.left,i.left+s>u&&u>s?Math.abs(i.left+s-u):0),i.top-=Math.min(i.top,i.top+n>c&&c>n?Math.abs(n+o):0),i},_findPos:function(t){for(var i,a=this._getInst(t),s=this._get(a,"isRTL");t&&("hidden"===t.type||1!==t.nodeType||e.expr.filters.hidden(t));)t=t[s?"previousSibling":"nextSibling"];return i=e(t).offset(),[i.left,i.top]},_hideDatepicker:function(t){var i,a,s,n,o=this._curInst;!o||t&&o!==e.data(t,r)||this._datepickerShowing&&(i=this._get(o,"showAnim"),a=this._get(o,"duration"),s=function(){e.datepicker._tidyDialog(o)},e.effects&&(e.effects.effect[i]||e.effects[i])?o.dpDiv.hide(i,e.datepicker._get(o,"showOptions"),a,s):o.dpDiv["slideDown"===i?"slideUp":"fadeIn"===i?"fadeOut":"hide"](i?a:null,s),i||s(),this._datepickerShowing=!1,n=this._get(o,"onClose"),n&&n.apply(o.input?o.input[0]:null,[o.input?o.input.val():"",o]),this._lastInput=null,this._inDialog&&(this._dialogInput.css({position:"absolute",left:"0",top:"-100px"}),e.blockUI&&(e.unblockUI(),e("body").append(this.dpDiv))),this._inDialog=!1)},_tidyDialog:function(e){e.dpDiv.removeClass(this._dialogClass).unbind(".ui-datepicker-calendar")},_checkExternalClick:function(t){if(e.datepicker._curInst){var i=e(t.target),a=e.datepicker._getInst(i[0]);(i[0].id!==e.datepicker._mainDivId&&0===i.parents("#"+e.datepicker._mainDivId).length&&!i.hasClass(e.datepicker.markerClassName)&&!i.closest("."+e.datepicker._triggerClass).length&&e.datepicker._datepickerShowing&&(!e.datepicker._inDialog||!e.blockUI)||i.hasClass(e.datepicker.markerClassName)&&e.datepicker._curInst!==a)&&e.datepicker._hideDatepicker()}},_adjustDate:function(t,i,a){var s=e(t),n=this._getInst(s[0]);this._isDisabledDatepicker(s[0])||(this._adjustInstDate(n,i+("M"===a?this._get(n,"showCurrentAtPos"):0),a),this._updateDatepicker(n))},_gotoToday:function(t){var i,a=e(t),s=this._getInst(a[0]);this._get(s,"gotoCurrent")&&s.currentDay?(s.selectedDay=s.currentDay,s.drawMonth=s.selectedMonth=s.currentMonth,s.drawYear=s.selectedYear=s.currentYear):(i=new Date,s.selectedDay=i.getDate(),s.drawMonth=s.selectedMonth=i.getMonth(),s.drawYear=s.selectedYear=i.getFullYear()),this._notifyChange(s),this._adjustDate(a)},_selectMonthYear:function(t,i,a){var s=e(t),n=this._getInst(s[0]);n["selected"+("M"===a?"Month":"Year")]=n["draw"+("M"===a?"Month":"Year")]=parseInt(i.options[i.selectedIndex].value,10),this._notifyChange(n),this._adjustDate(s)},_selectDay:function(t,i,a,s){var n,r=e(t);e(s).hasClass(this._unselectableClass)||this._isDisabledDatepicker(r[0])||(n=this._getInst(r[0]),n.selectedDay=n.currentDay=e("a",s).html(),n.selectedMonth=n.currentMonth=i,n.selectedYear=n.currentYear=a,this._selectDate(t,this._formatDate(n,n.currentDay,n.currentMonth,n.currentYear)))},_clearDate:function(t){var i=e(t);this._selectDate(i,"")},_selectDate:function(t,i){var a,s=e(t),n=this._getInst(s[0]);i=null!=i?i:this._formatDate(n),n.input&&n.input.val(i),this._updateAlternate(n),a=this._get(n,"onSelect"),a?a.apply(n.input?n.input[0]:null,[i,n]):n.input&&n.input.trigger("change"),n.inline?this._updateDatepicker(n):(this._hideDatepicker(),this._lastInput=n.input[0],"object"!=typeof n.input[0]&&n.input.focus(),this._lastInput=null)},_updateAlternate:function(t){var i,a,s,n=this._get(t,"altField");n&&(i=this._get(t,"altFormat")||this._get(t,"dateFormat"),a=this._getDate(t),s=this.formatDate(i,a,this._getFormatConfig(t)),e(n).each(function(){e(this).val(s)}))},noWeekends:function(e){var t=e.getDay();return[t>0&&6>t,""]},iso8601Week:function(e){var t,i=new Date(e.getTime());return i.setDate(i.getDate()+4-(i.getDay()||7)),t=i.getTime(),i.setMonth(0),i.setDate(1),Math.floor(Math.round((t-i)/864e5)/7)+1},parseDate:function(i,a,s){if(null==i||null==a)throw"Invalid arguments";if(a="object"==typeof a?""+a:a+"",""===a)return null;var n,r,o,u,c=0,h=(s?s.shortYearCutoff:null)||this._defaults.shortYearCutoff,l="string"!=typeof h?h:(new Date).getFullYear()%100+parseInt(h,10),d=(s?s.dayNamesShort:null)||this._defaults.dayNamesShort,p=(s?s.dayNames:null)||this._defaults.dayNames,g=(s?s.monthNamesShort:null)||this._defaults.monthNamesShort,m=(s?s.monthNames:null)||this._defaults.monthNames,f=-1,_=-1,v=-1,k=-1,y=!1,b=function(e){var t=i.length>n+1&&i.charAt(n+1)===e;return t&&n++,t},D=function(e){var t=b(e),i="@"===e?14:"!"===e?20:"y"===e&&t?4:"o"===e?3:2,s=RegExp("^\\d{1,"+i+"}"),n=a.substring(c).match(s);if(!n)throw"Missing number at position "+c;return c+=n[0].length,parseInt(n[0],10)},w=function(i,s,n){var r=-1,o=e.map(b(i)?n:s,function(e,t){return[[t,e]]}).sort(function(e,t){return-(e[1].length-t[1].length)});if(e.each(o,function(e,i){var s=i[1];return a.substr(c,s.length).toLowerCase()===s.toLowerCase()?(r=i[0],c+=s.length,!1):t}),-1!==r)return r+1;throw"Unknown name at position "+c},M=function(){if(a.charAt(c)!==i.charAt(n))throw"Unexpected literal at position "+c;c++};for(n=0;i.length>n;n++)if(y)"'"!==i.charAt(n)||b("'")?M():y=!1;else switch(i.charAt(n)){case"d":v=D("d");break;case"D":w("D",d,p);break;case"o":k=D("o");break;case"m":_=D("m");break;case"M":_=w("M",g,m);break;case"y":f=D("y");break;case"@":u=new Date(D("@")),f=u.getFullYear(),_=u.getMonth()+1,v=u.getDate();break;case"!":u=new Date((D("!")-this._ticksTo1970)/1e4),f=u.getFullYear(),_=u.getMonth()+1,v=u.getDate();break;case"'":b("'")?M():y=!0;break;default:M()}if(a.length>c&&(o=a.substr(c),!/^\s+/.test(o)))throw"Extra/unparsed characters found in date: "+o;if(-1===f?f=(new Date).getFullYear():100>f&&(f+=(new Date).getFullYear()-(new Date).getFullYear()%100+(l>=f?0:-100)),k>-1)for(_=1,v=k;;){if(r=this._getDaysInMonth(f,_-1),r>=v)break;_++,v-=r}if(u=this._daylightSavingAdjust(new Date(f,_-1,v)),u.getFullYear()!==f||u.getMonth()+1!==_||u.getDate()!==v)throw"Invalid date";return u},ATOM:"yy-mm-dd",COOKIE:"D, dd M yy",ISO_8601:"yy-mm-dd",RFC_822:"D, d M y",RFC_850:"DD, dd-M-y",RFC_1036:"D, d M y",RFC_1123:"D, d M yy",RFC_2822:"D, d M yy",RSS:"D, d M y",TICKS:"!",TIMESTAMP:"@",W3C:"yy-mm-dd",_ticksTo1970:1e7*60*60*24*(718685+Math.floor(492.5)-Math.floor(19.7)+Math.floor(4.925)),formatDate:function(e,t,i){if(!t)return"";var a,s=(i?i.dayNamesShort:null)||this._defaults.dayNamesShort,n=(i?i.dayNames:null)||this._defaults.dayNames,r=(i?i.monthNamesShort:null)||this._defaults.monthNamesShort,o=(i?i.monthNames:null)||this._defaults.monthNames,u=function(t){var i=e.length>a+1&&e.charAt(a+1)===t;return i&&a++,i},c=function(e,t,i){var a=""+t;if(u(e))for(;i>a.length;)a="0"+a;return a},h=function(e,t,i,a){return u(e)?a[t]:i[t]},l="",d=!1;if(t)for(a=0;e.length>a;a++)if(d)"'"!==e.charAt(a)||u("'")?l+=e.charAt(a):d=!1;else switch(e.charAt(a)){case"d":l+=c("d",t.getDate(),2);break;case"D":l+=h("D",t.getDay(),s,n);break;case"o":l+=c("o",Math.round((new Date(t.getFullYear(),t.getMonth(),t.getDate()).getTime()-new Date(t.getFullYear(),0,0).getTime())/864e5),3);break;case"m":l+=c("m",t.getMonth()+1,2);break;case"M":l+=h("M",t.getMonth(),r,o);break;case"y":l+=u("y")?t.getFullYear():(10>t.getYear()%100?"0":"")+t.getYear()%100;break;case"@":l+=t.getTime();break;case"!":l+=1e4*t.getTime()+this._ticksTo1970;break;case"'":u("'")?l+="'":d=!0;break;default:l+=e.charAt(a)}return l},_possibleChars:function(e){var t,i="",a=!1,s=function(i){var a=e.length>t+1&&e.charAt(t+1)===i;return a&&t++,a};for(t=0;e.length>t;t++)if(a)"'"!==e.charAt(t)||s("'")?i+=e.charAt(t):a=!1;else switch(e.charAt(t)){case"d":case"m":case"y":case"@":i+="0123456789";break;case"D":case"M":return null;case"'":s("'")?i+="'":a=!0;break;default:i+=e.charAt(t)}return i},_get:function(e,i){return e.settings[i]!==t?e.settings[i]:this._defaults[i]},_setDateFromField:function(e,t){if(e.input.val()!==e.lastVal){var i=this._get(e,"dateFormat"),a=e.lastVal=e.input?e.input.val():null,s=this._getDefaultDate(e),n=s,r=this._getFormatConfig(e);try{n=this.parseDate(i,a,r)||s}catch(o){a=t?"":a}e.selectedDay=n.getDate(),e.drawMonth=e.selectedMonth=n.getMonth(),e.drawYear=e.selectedYear=n.getFullYear(),e.currentDay=a?n.getDate():0,e.currentMonth=a?n.getMonth():0,e.currentYear=a?n.getFullYear():0,this._adjustInstDate(e)}},_getDefaultDate:function(e){return this._restrictMinMax(e,this._determineDate(e,this._get(e,"defaultDate"),new Date))},_determineDate:function(t,i,a){var s=function(e){var t=new Date;return t.setDate(t.getDate()+e),t},n=function(i){try{return e.datepicker.parseDate(e.datepicker._get(t,"dateFormat"),i,e.datepicker._getFormatConfig(t))}catch(a){}for(var s=(i.toLowerCase().match(/^c/)?e.datepicker._getDate(t):null)||new Date,n=s.getFullYear(),r=s.getMonth(),o=s.getDate(),u=/([+\-]?[0-9]+)\s*(d|D|w|W|m|M|y|Y)?/g,c=u.exec(i);c;){switch(c[2]||"d"){case"d":case"D":o+=parseInt(c[1],10);break;case"w":case"W":o+=7*parseInt(c[1],10);break;case"m":case"M":r+=parseInt(c[1],10),o=Math.min(o,e.datepicker._getDaysInMonth(n,r));break;case"y":case"Y":n+=parseInt(c[1],10),o=Math.min(o,e.datepicker._getDaysInMonth(n,r))}c=u.exec(i)}return new Date(n,r,o)},r=null==i||""===i?a:"string"==typeof i?n(i):"number"==typeof i?isNaN(i)?a:s(i):new Date(i.getTime());return r=r&&"Invalid Date"==""+r?a:r,r&&(r.setHours(0),r.setMinutes(0),r.setSeconds(0),r.setMilliseconds(0)),this._daylightSavingAdjust(r)},_daylightSavingAdjust:function(e){return e?(e.setHours(e.getHours()>12?e.getHours()+2:0),e):null},_setDate:function(e,t,i){var a=!t,s=e.selectedMonth,n=e.selectedYear,r=this._restrictMinMax(e,this._determineDate(e,t,new Date));e.selectedDay=e.currentDay=r.getDate(),e.drawMonth=e.selectedMonth=e.currentMonth=r.getMonth(),e.drawYear=e.selectedYear=e.currentYear=r.getFullYear(),s===e.selectedMonth&&n===e.selectedYear||i||this._notifyChange(e),this._adjustInstDate(e),e.input&&e.input.val(a?"":this._formatDate(e))},_getDate:function(e){var t=!e.currentYear||e.input&&""===e.input.val()?null:this._daylightSavingAdjust(new Date(e.currentYear,e.currentMonth,e.currentDay));return t},_attachHandlers:function(t){var i=this._get(t,"stepMonths"),a="#"+t.id.replace(/\\\\/g,"\\");t.dpDiv.find("[data-handler]").map(function(){var t={prev:function(){e.datepicker._adjustDate(a,-i,"M")},next:function(){e.datepicker._adjustDate(a,+i,"M")},hide:function(){e.datepicker._hideDatepicker()},today:function(){e.datepicker._gotoToday(a)},selectDay:function(){return e.datepicker._selectDay(a,+this.getAttribute("data-month"),+this.getAttribute("data-year"),this),!1},selectMonth:function(){return e.datepicker._selectMonthYear(a,this,"M"),!1},selectYear:function(){return e.datepicker._selectMonthYear(a,this,"Y"),!1}};e(this).bind(this.getAttribute("data-event"),t[this.getAttribute("data-handler")])})},_generateHTML:function(e){var t,i,a,s,n,r,o,u,c,h,l,d,p,g,m,f,_,v,k,y,b,D,w,M,C,x,I,N,T,A,E,S,Y,F,P,O,j,K,R,H=new Date,W=this._daylightSavingAdjust(new Date(H.getFullYear(),H.getMonth(),H.getDate())),L=this._get(e,"isRTL"),U=this._get(e,"showButtonPanel"),B=this._get(e,"hideIfNoPrevNext"),z=this._get(e,"navigationAsDateFormat"),q=this._getNumberOfMonths(e),G=this._get(e,"showCurrentAtPos"),J=this._get(e,"stepMonths"),Q=1!==q[0]||1!==q[1],V=this._daylightSavingAdjust(e.currentDay?new Date(e.currentYear,e.currentMonth,e.currentDay):new Date(9999,9,9)),$=this._getMinMaxDate(e,"min"),X=this._getMinMaxDate(e,"max"),Z=e.drawMonth-G,et=e.drawYear;if(0>Z&&(Z+=12,et--),X)for(t=this._daylightSavingAdjust(new Date(X.getFullYear(),X.getMonth()-q[0]*q[1]+1,X.getDate())),t=$&&$>t?$:t;this._daylightSavingAdjust(new Date(et,Z,1))>t;)Z--,0>Z&&(Z=11,et--);for(e.drawMonth=Z,e.drawYear=et,i=this._get(e,"prevText"),i=z?this.formatDate(i,this._daylightSavingAdjust(new Date(et,Z-J,1)),this._getFormatConfig(e)):i,a=this._canAdjustMonth(e,-1,et,Z)?"<a class='ui-datepicker-prev ui-corner-all' data-handler='prev' data-event='click' title='"+i+"'><span class='ui-icon ui-icon-circle-triangle-"+(L?"e":"w")+"'>"+i+"</span></a>":B?"":"<a class='ui-datepicker-prev ui-corner-all ui-state-disabled' title='"+i+"'><span class='ui-icon ui-icon-circle-triangle-"+(L?"e":"w")+"'>"+i+"</span></a>",s=this._get(e,"nextText"),s=z?this.formatDate(s,this._daylightSavingAdjust(new Date(et,Z+J,1)),this._getFormatConfig(e)):s,n=this._canAdjustMonth(e,1,et,Z)?"<a class='ui-datepicker-next ui-corner-all' data-handler='next' data-event='click' title='"+s+"'><span class='ui-icon ui-icon-circle-triangle-"+(L?"w":"e")+"'>"+s+"</span></a>":B?"":"<a class='ui-datepicker-next ui-corner-all ui-state-disabled' title='"+s+"'><span class='ui-icon ui-icon-circle-triangle-"+(L?"w":"e")+"'>"+s+"</span></a>",r=this._get(e,"currentText"),o=this._get(e,"gotoCurrent")&&e.currentDay?V:W,r=z?this.formatDate(r,o,this._getFormatConfig(e)):r,u=e.inline?"":"<button type='button' class='ui-datepicker-close ui-state-default ui-priority-primary ui-corner-all' data-handler='hide' data-event='click'>"+this._get(e,"closeText")+"</button>",c=U?"<div class='ui-datepicker-buttonpane ui-widget-content'>"+(L?u:"")+(this._isInRange(e,o)?"<button type='button' class='ui-datepicker-current ui-state-default ui-priority-secondary ui-corner-all' data-handler='today' data-event='click'>"+r+"</button>":"")+(L?"":u)+"</div>":"",h=parseInt(this._get(e,"firstDay"),10),h=isNaN(h)?0:h,l=this._get(e,"showWeek"),d=this._get(e,"dayNames"),p=this._get(e,"dayNamesMin"),g=this._get(e,"monthNames"),m=this._get(e,"monthNamesShort"),f=this._get(e,"beforeShowDay"),_=this._get(e,"showOtherMonths"),v=this._get(e,"selectOtherMonths"),k=this._getDefaultDate(e),y="",D=0;q[0]>D;D++){for(w="",this.maxRows=4,M=0;q[1]>M;M++){if(C=this._daylightSavingAdjust(new Date(et,Z,e.selectedDay)),x=" ui-corner-all",I="",Q){if(I+="<div class='ui-datepicker-group",q[1]>1)switch(M){case 0:I+=" ui-datepicker-group-first",x=" ui-corner-"+(L?"right":"left");break;case q[1]-1:I+=" ui-datepicker-group-last",x=" ui-corner-"+(L?"left":"right");break;default:I+=" ui-datepicker-group-middle",x=""}I+="'>"}for(I+="<div class='ui-datepicker-header ui-widget-header ui-helper-clearfix"+x+"'>"+(/all|left/.test(x)&&0===D?L?n:a:"")+(/all|right/.test(x)&&0===D?L?a:n:"")+this._generateMonthYearHeader(e,Z,et,$,X,D>0||M>0,g,m)+"</div><table class='ui-datepicker-calendar'><thead>"+"<tr>",N=l?"<th class='ui-datepicker-week-col'>"+this._get(e,"weekHeader")+"</th>":"",b=0;7>b;b++)T=(b+h)%7,N+="<th"+((b+h+6)%7>=5?" class='ui-datepicker-week-end'":"")+">"+"<span title='"+d[T]+"'>"+p[T]+"</span></th>";for(I+=N+"</tr></thead><tbody>",A=this._getDaysInMonth(et,Z),et===e.selectedYear&&Z===e.selectedMonth&&(e.selectedDay=Math.min(e.selectedDay,A)),E=(this._getFirstDayOfMonth(et,Z)-h+7)%7,S=Math.ceil((E+A)/7),Y=Q?this.maxRows>S?this.maxRows:S:S,this.maxRows=Y,F=this._daylightSavingAdjust(new Date(et,Z,1-E)),P=0;Y>P;P++){for(I+="<tr>",O=l?"<td class='ui-datepicker-week-col'>"+this._get(e,"calculateWeek")(F)+"</td>":"",b=0;7>b;b++)j=f?f.apply(e.input?e.input[0]:null,[F]):[!0,""],K=F.getMonth()!==Z,R=K&&!v||!j[0]||$&&$>F||X&&F>X,O+="<td class='"+((b+h+6)%7>=5?" ui-datepicker-week-end":"")+(K?" ui-datepicker-other-month":"")+(F.getTime()===C.getTime()&&Z===e.selectedMonth&&e._keyEvent||k.getTime()===F.getTime()&&k.getTime()===C.getTime()?" "+this._dayOverClass:"")+(R?" "+this._unselectableClass+" ui-state-disabled":"")+(K&&!_?"":" "+j[1]+(F.getTime()===V.getTime()?" "+this._currentClass:"")+(F.getTime()===W.getTime()?" ui-datepicker-today":""))+"'"+(K&&!_||!j[2]?"":" title='"+j[2].replace(/'/g,"&#39;")+"'")+(R?"":" data-handler='selectDay' data-event='click' data-month='"+F.getMonth()+"' data-year='"+F.getFullYear()+"'")+">"+(K&&!_?"&#xa0;":R?"<span class='ui-state-default'>"+F.getDate()+"</span>":"<a class='ui-state-default"+(F.getTime()===W.getTime()?" ui-state-highlight":"")+(F.getTime()===V.getTime()?" ui-state-active":"")+(K?" ui-priority-secondary":"")+"' href='#'>"+F.getDate()+"</a>")+"</td>",F.setDate(F.getDate()+1),F=this._daylightSavingAdjust(F);I+=O+"</tr>"}Z++,Z>11&&(Z=0,et++),I+="</tbody></table>"+(Q?"</div>"+(q[0]>0&&M===q[1]-1?"<div class='ui-datepicker-row-break'></div>":""):""),w+=I}y+=w}return y+=c,e._keyEvent=!1,y},_generateMonthYearHeader:function(e,t,i,a,s,n,r,o){var u,c,h,l,d,p,g,m,f=this._get(e,"changeMonth"),_=this._get(e,"changeYear"),v=this._get(e,"showMonthAfterYear"),k="<div class='ui-datepicker-title'>",y="";if(n||!f)y+="<span class='ui-datepicker-month'>"+r[t]+"</span>";else{for(u=a&&a.getFullYear()===i,c=s&&s.getFullYear()===i,y+="<select class='ui-datepicker-month' data-handler='selectMonth' data-event='change'>",h=0;12>h;h++)(!u||h>=a.getMonth())&&(!c||s.getMonth()>=h)&&(y+="<option value='"+h+"'"+(h===t?" selected='selected'":"")+">"+o[h]+"</option>");y+="</select>"}if(v||(k+=y+(!n&&f&&_?"":"&#xa0;")),!e.yearshtml)if(e.yearshtml="",n||!_)k+="<span class='ui-datepicker-year'>"+i+"</span>";else{for(l=this._get(e,"yearRange").split(":"),d=(new Date).getFullYear(),p=function(e){var t=e.match(/c[+\-].*/)?i+parseInt(e.substring(1),10):e.match(/[+\-].*/)?d+parseInt(e,10):parseInt(e,10);
return isNaN(t)?d:t},g=p(l[0]),m=Math.max(g,p(l[1]||"")),g=a?Math.max(g,a.getFullYear()):g,m=s?Math.min(m,s.getFullYear()):m,e.yearshtml+="<select class='ui-datepicker-year' data-handler='selectYear' data-event='change'>";m>=g;g++)e.yearshtml+="<option value='"+g+"'"+(g===i?" selected='selected'":"")+">"+g+"</option>";e.yearshtml+="</select>",k+=e.yearshtml,e.yearshtml=null}return k+=this._get(e,"yearSuffix"),v&&(k+=(!n&&f&&_?"":"&#xa0;")+y),k+="</div>"},_adjustInstDate:function(e,t,i){var a=e.drawYear+("Y"===i?t:0),s=e.drawMonth+("M"===i?t:0),n=Math.min(e.selectedDay,this._getDaysInMonth(a,s))+("D"===i?t:0),r=this._restrictMinMax(e,this._daylightSavingAdjust(new Date(a,s,n)));e.selectedDay=r.getDate(),e.drawMonth=e.selectedMonth=r.getMonth(),e.drawYear=e.selectedYear=r.getFullYear(),("M"===i||"Y"===i)&&this._notifyChange(e)},_restrictMinMax:function(e,t){var i=this._getMinMaxDate(e,"min"),a=this._getMinMaxDate(e,"max"),s=i&&i>t?i:t;return a&&s>a?a:s},_notifyChange:function(e){var t=this._get(e,"onChangeMonthYear");t&&t.apply(e.input?e.input[0]:null,[e.selectedYear,e.selectedMonth+1,e])},_getNumberOfMonths:function(e){var t=this._get(e,"numberOfMonths");return null==t?[1,1]:"number"==typeof t?[1,t]:t},_getMinMaxDate:function(e,t){return this._determineDate(e,this._get(e,t+"Date"),null)},_getDaysInMonth:function(e,t){return 32-this._daylightSavingAdjust(new Date(e,t,32)).getDate()},_getFirstDayOfMonth:function(e,t){return new Date(e,t,1).getDay()},_canAdjustMonth:function(e,t,i,a){var s=this._getNumberOfMonths(e),n=this._daylightSavingAdjust(new Date(i,a+(0>t?t:s[0]*s[1]),1));return 0>t&&n.setDate(this._getDaysInMonth(n.getFullYear(),n.getMonth())),this._isInRange(e,n)},_isInRange:function(e,t){var i,a,s=this._getMinMaxDate(e,"min"),n=this._getMinMaxDate(e,"max"),r=null,o=null,u=this._get(e,"yearRange");return u&&(i=u.split(":"),a=(new Date).getFullYear(),r=parseInt(i[0],10),o=parseInt(i[1],10),i[0].match(/[+\-].*/)&&(r+=a),i[1].match(/[+\-].*/)&&(o+=a)),(!s||t.getTime()>=s.getTime())&&(!n||t.getTime()<=n.getTime())&&(!r||t.getFullYear()>=r)&&(!o||o>=t.getFullYear())},_getFormatConfig:function(e){var t=this._get(e,"shortYearCutoff");return t="string"!=typeof t?t:(new Date).getFullYear()%100+parseInt(t,10),{shortYearCutoff:t,dayNamesShort:this._get(e,"dayNamesShort"),dayNames:this._get(e,"dayNames"),monthNamesShort:this._get(e,"monthNamesShort"),monthNames:this._get(e,"monthNames")}},_formatDate:function(e,t,i,a){t||(e.currentDay=e.selectedDay,e.currentMonth=e.selectedMonth,e.currentYear=e.selectedYear);var s=t?"object"==typeof t?t:this._daylightSavingAdjust(new Date(a,i,t)):this._daylightSavingAdjust(new Date(e.currentYear,e.currentMonth,e.currentDay));return this.formatDate(this._get(e,"dateFormat"),s,this._getFormatConfig(e))}}),e.fn.datepicker=function(t){if(!this.length)return this;e.datepicker.initialized||(e(document).mousedown(e.datepicker._checkExternalClick),e.datepicker.initialized=!0),0===e("#"+e.datepicker._mainDivId).length&&e("body").append(e.datepicker.dpDiv);var i=Array.prototype.slice.call(arguments,1);return"string"!=typeof t||"isDisabled"!==t&&"getDate"!==t&&"widget"!==t?"option"===t&&2===arguments.length&&"string"==typeof arguments[1]?e.datepicker["_"+t+"Datepicker"].apply(e.datepicker,[this[0]].concat(i)):this.each(function(){"string"==typeof t?e.datepicker["_"+t+"Datepicker"].apply(e.datepicker,[this].concat(i)):e.datepicker._attachDatepicker(this,t)}):e.datepicker["_"+t+"Datepicker"].apply(e.datepicker,[this[0]].concat(i))},e.datepicker=new i,e.datepicker.initialized=!1,e.datepicker.uuid=(new Date).getTime(),e.datepicker.version="1.10.4"})(jQuery);
$(document).ready(function() {
	var jczqMatchs = $("#jczq_matchs");
	var jclqMatchs = $("#jclq_matchs");
	$(".match-nav .li-bg").mouseover(function() {
		jczqMatchs.css("margin-top","78px");
		jclqMatchs.css("margin-top","78px");
		$(".jj-layer").show();
	});
	$(".match-nav .li-bg").mouseout(function() {
		jczqMatchs.css("margin-top","0px");
		jclqMatchs.css("margin-top","0px");
		$(".jj-layer").hide();
	});
	
	$(".jj-nav-sp").mouseover(function() {
		$(".jj-nav-sp ul").show();
	});
	$(".jj-nav-sp").mouseout(function() {
		$(".jj-nav-sp ul").hide();
	});
	
	$('.jj-nav-calendar').datepicker({
        dateFormat: 'yy-mm-dd',
        prevText: '上月',
        nextText: '下月',
        dayNamesMin: ['日','一','二','三','四','五','六'],
        monthNames: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月']
    });
});

//赛事后面的小三角，点击聚焦的实话，旋转180度
$(function(){
   $(".jj-nav-match").bind("mouseover", function() {
      $(".icon_dwn").addClass("flipy");
   }).bind("mouseout", function() {
    $(".icon_dwn").removeClass("flipy");	
   });
});

//竞猜胜平负SP后面的小三角，点击聚焦的实话，旋转180度
$(function(){
   $(".jj-nav-sp").bind("mouseover", function() {
      $(".icon_dwnb").addClass("flipy");
   }).bind("mouseout", function() {
    $(".icon_dwnb").removeClass("flipy");	
   });
 });

//日历框后面的小三角，点击聚焦的实话，旋转180度
$(function(){
   $(".jj-nav-time").bind("mouseover", function() {
      $(".icon_dwnc").addClass("flipy");
   }).bind("mouseout", function() {
    $(".icon_dwnc").removeClass("flipy");	
   });
 });
var pageBody = {
		ul_start:'<ul class="pager">',
		ul_end:	'</ul>',
		pre:'<li class="last" style="float:left"><a data-page="{0}" href="{1}&page={0}" ><span>上一页</span><i></i></a></li>',
		next:'<li class="next" style="float:left"><a data-page="{0}" href="{1}&page={0}" ><span>下一页</span><i></i></a></li>',
		pageActive:'<li class="active" style="float:left"><a data-page="{0}" href="{1}&page={0}" >{0}</a></li>',
		page:'<li style="float:left"><a data-page="{0}" href="{1}&page={0}" >{0}</a></li>',
		pageOmit:'<li  style="float:left">...</li>'
};


function ajaxPager(url, curPage, totalPage){
	var prev = curPage - 1;
	if(prev < 1 ){
		prev = 1;
	}
	var next = curPage + 1;
	if(next > totalPage){
		next = totalPage;
	}
	var width = 2;
	var ulHtml = pageBody.ul_start;
	if(prev != curPage){
		ulHtml += $.format(pageBody.pre,prev,url);
	}
	var array = paging(curPage, totalPage, width);
	for ( var i = 0; i < array.length; i++) {
		if(array[i] == "" || array[i] == " "){
			continue;
		}
		if(array[i] == -1){
			ulHtml += pageBody.pageOmit;
		} else {
			if(array[i] == curPage){
				ulHtml += $.format(pageBody.pageActive,array[i],url);
			} else {
				ulHtml += $.format(pageBody.page,array[i],url);
			}
		}
	}
	if(next != totalPage){
		ulHtml += $.format(pageBody.next,next,url);
	}
	return ulHtml;
}

function paging(currentPage, totalPages, width) {
	var pages = '';
	if (totalPages <= 1){
		return pages;
	}
	var index = currentPage;
	var remains = width+1;
	while(index > 0){
		pages = index + ' ' + pages;
		index--;
		remains--;
		if (remains == 0) {
			break;
		}
	}
	if (remains == 0 && index > 1){
		pages = '-1 '  + pages;	// ... 标志页号
		pages = '1 '  + pages;
	}else{
		var tmpArr = pages.split(" ");
		if (tmpArr[0] != 1){
			pages = '1 '  + pages;
		}
	}
	index = currentPage+1;
	remains = width;
	while(index <= totalPages){
		pages = pages + " " +index;
		index++;
		remains--;
		if (remains == 0) {
			break;
		}
	}
	pages = $.trim(pages);
	if (remains == 0 && index < totalPages){
		pages += ' -1';	// ... 标志页号
		pages += ' ' + totalPages;
	}else{
		var tmpArr = pages.split(" ");
		if (tmpArr[tmpArr.length-1] != totalPages){
			pages += ' ' + totalPages;
		}
	}
	return pages.split(' ');
}
/*
* jQuery Form Plugin; v20131121
* http://jquery.malsup.com/form/
* Copyright (c) 2013 M. Alsup; Dual licensed: MIT/GPL
* https://github.com/malsup/form#copyright-and-license
*/
;(function(e){"function"==typeof define&&define.amd?define(["jquery"],e):e("undefined"!=typeof jQuery?jQuery:window.Zepto)})(function(e){"use strict";function t(t){var r=t.data;t.isDefaultPrevented()||(t.preventDefault(),e(t.target).ajaxSubmit(r))}function r(t){var r=t.target,a=e(r);if(!a.is("[type=submit],[type=image]")){var n=a.closest("[type=submit]");if(0===n.length)return;r=n[0]}var i=this;if(i.clk=r,"image"==r.type)if(void 0!==t.offsetX)i.clk_x=t.offsetX,i.clk_y=t.offsetY;else if("function"==typeof e.fn.offset){var o=a.offset();i.clk_x=t.pageX-o.left,i.clk_y=t.pageY-o.top}else i.clk_x=t.pageX-r.offsetLeft,i.clk_y=t.pageY-r.offsetTop;setTimeout(function(){i.clk=i.clk_x=i.clk_y=null},100)}function a(){if(e.fn.ajaxSubmit.debug){var t="[jquery.form] "+Array.prototype.join.call(arguments,"");window.console&&window.console.log?window.console.log(t):window.opera&&window.opera.postError&&window.opera.postError(t)}}var n={};n.fileapi=void 0!==e("<input type='file'/>").get(0).files,n.formdata=void 0!==window.FormData;var i=!!e.fn.prop;e.fn.attr2=function(){if(!i)return this.attr.apply(this,arguments);var e=this.prop.apply(this,arguments);return e&&e.jquery||"string"==typeof e?e:this.attr.apply(this,arguments)},e.fn.ajaxSubmit=function(t){function r(r){var a,n,i=e.param(r,t.traditional).split("&"),o=i.length,s=[];for(a=0;o>a;a++)i[a]=i[a].replace(/\+/g," "),n=i[a].split("="),s.push([decodeURIComponent(n[0]),decodeURIComponent(n[1])]);return s}function o(a){for(var n=new FormData,i=0;a.length>i;i++)n.append(a[i].name,a[i].value);if(t.extraData){var o=r(t.extraData);for(i=0;o.length>i;i++)o[i]&&n.append(o[i][0],o[i][1])}t.data=null;var s=e.extend(!0,{},e.ajaxSettings,t,{contentType:!1,processData:!1,cache:!1,type:u||"POST"});t.uploadProgress&&(s.xhr=function(){var r=e.ajaxSettings.xhr();return r.upload&&r.upload.addEventListener("progress",function(e){var r=0,a=e.loaded||e.position,n=e.total;e.lengthComputable&&(r=Math.ceil(100*(a/n))),t.uploadProgress(e,a,n,r)},!1),r}),s.data=null;var c=s.beforeSend;return s.beforeSend=function(e,r){r.data=t.formData?t.formData:n,c&&c.call(this,e,r)},e.ajax(s)}function s(r){function n(e){var t=null;try{e.contentWindow&&(t=e.contentWindow.document)}catch(r){a("cannot get iframe.contentWindow document: "+r)}if(t)return t;try{t=e.contentDocument?e.contentDocument:e.document}catch(r){a("cannot get iframe.contentDocument: "+r),t=e.document}return t}function o(){function t(){try{var e=n(g).readyState;a("state = "+e),e&&"uninitialized"==e.toLowerCase()&&setTimeout(t,50)}catch(r){a("Server abort: ",r," (",r.name,")"),s(k),j&&clearTimeout(j),j=void 0}}var r=f.attr2("target"),i=f.attr2("action");w.setAttribute("target",p),(!u||/post/i.test(u))&&w.setAttribute("method","POST"),i!=m.url&&w.setAttribute("action",m.url),m.skipEncodingOverride||u&&!/post/i.test(u)||f.attr({encoding:"multipart/form-data",enctype:"multipart/form-data"}),m.timeout&&(j=setTimeout(function(){T=!0,s(D)},m.timeout));var o=[];try{if(m.extraData)for(var c in m.extraData)m.extraData.hasOwnProperty(c)&&(e.isPlainObject(m.extraData[c])&&m.extraData[c].hasOwnProperty("name")&&m.extraData[c].hasOwnProperty("value")?o.push(e('<input type="hidden" name="'+m.extraData[c].name+'">').val(m.extraData[c].value).appendTo(w)[0]):o.push(e('<input type="hidden" name="'+c+'">').val(m.extraData[c]).appendTo(w)[0]));m.iframeTarget||v.appendTo("body"),g.attachEvent?g.attachEvent("onload",s):g.addEventListener("load",s,!1),setTimeout(t,15);try{w.submit()}catch(l){var d=document.createElement("form").submit;d.apply(w)}}finally{w.setAttribute("action",i),r?w.setAttribute("target",r):f.removeAttr("target"),e(o).remove()}}function s(t){if(!x.aborted&&!F){if(M=n(g),M||(a("cannot access response document"),t=k),t===D&&x)return x.abort("timeout"),S.reject(x,"timeout"),void 0;if(t==k&&x)return x.abort("server abort"),S.reject(x,"error","server abort"),void 0;if(M&&M.location.href!=m.iframeSrc||T){g.detachEvent?g.detachEvent("onload",s):g.removeEventListener("load",s,!1);var r,i="success";try{if(T)throw"timeout";var o="xml"==m.dataType||M.XMLDocument||e.isXMLDoc(M);if(a("isXml="+o),!o&&window.opera&&(null===M.body||!M.body.innerHTML)&&--O)return a("requeing onLoad callback, DOM not available"),setTimeout(s,250),void 0;var u=M.body?M.body:M.documentElement;x.responseText=u?u.innerHTML:null,x.responseXML=M.XMLDocument?M.XMLDocument:M,o&&(m.dataType="xml"),x.getResponseHeader=function(e){var t={"content-type":m.dataType};return t[e.toLowerCase()]},u&&(x.status=Number(u.getAttribute("status"))||x.status,x.statusText=u.getAttribute("statusText")||x.statusText);var c=(m.dataType||"").toLowerCase(),l=/(json|script|text)/.test(c);if(l||m.textarea){var f=M.getElementsByTagName("textarea")[0];if(f)x.responseText=f.value,x.status=Number(f.getAttribute("status"))||x.status,x.statusText=f.getAttribute("statusText")||x.statusText;else if(l){var p=M.getElementsByTagName("pre")[0],h=M.getElementsByTagName("body")[0];p?x.responseText=p.textContent?p.textContent:p.innerText:h&&(x.responseText=h.textContent?h.textContent:h.innerText)}}else"xml"==c&&!x.responseXML&&x.responseText&&(x.responseXML=X(x.responseText));try{E=_(x,c,m)}catch(b){i="parsererror",x.error=r=b||i}}catch(b){a("error caught: ",b),i="error",x.error=r=b||i}x.aborted&&(a("upload aborted"),i=null),x.status&&(i=x.status>=200&&300>x.status||304===x.status?"success":"error"),"success"===i?(m.success&&m.success.call(m.context,E,"success",x),S.resolve(x.responseText,"success",x),d&&e.event.trigger("ajaxSuccess",[x,m])):i&&(void 0===r&&(r=x.statusText),m.error&&m.error.call(m.context,x,i,r),S.reject(x,"error",r),d&&e.event.trigger("ajaxError",[x,m,r])),d&&e.event.trigger("ajaxComplete",[x,m]),d&&!--e.active&&e.event.trigger("ajaxStop"),m.complete&&m.complete.call(m.context,x,i),F=!0,m.timeout&&clearTimeout(j),setTimeout(function(){m.iframeTarget?v.attr("src",m.iframeSrc):v.remove(),x.responseXML=null},100)}}}var c,l,m,d,p,v,g,x,b,y,T,j,w=f[0],S=e.Deferred();if(S.abort=function(e){x.abort(e)},r)for(l=0;h.length>l;l++)c=e(h[l]),i?c.prop("disabled",!1):c.removeAttr("disabled");if(m=e.extend(!0,{},e.ajaxSettings,t),m.context=m.context||m,p="jqFormIO"+(new Date).getTime(),m.iframeTarget?(v=e(m.iframeTarget),y=v.attr2("name"),y?p=y:v.attr2("name",p)):(v=e('<iframe name="'+p+'" src="'+m.iframeSrc+'" />'),v.css({position:"absolute",top:"-1000px",left:"-1000px"})),g=v[0],x={aborted:0,responseText:null,responseXML:null,status:0,statusText:"n/a",getAllResponseHeaders:function(){},getResponseHeader:function(){},setRequestHeader:function(){},abort:function(t){var r="timeout"===t?"timeout":"aborted";a("aborting upload... "+r),this.aborted=1;try{g.contentWindow.document.execCommand&&g.contentWindow.document.execCommand("Stop")}catch(n){}v.attr("src",m.iframeSrc),x.error=r,m.error&&m.error.call(m.context,x,r,t),d&&e.event.trigger("ajaxError",[x,m,r]),m.complete&&m.complete.call(m.context,x,r)}},d=m.global,d&&0===e.active++&&e.event.trigger("ajaxStart"),d&&e.event.trigger("ajaxSend",[x,m]),m.beforeSend&&m.beforeSend.call(m.context,x,m)===!1)return m.global&&e.active--,S.reject(),S;if(x.aborted)return S.reject(),S;b=w.clk,b&&(y=b.name,y&&!b.disabled&&(m.extraData=m.extraData||{},m.extraData[y]=b.value,"image"==b.type&&(m.extraData[y+".x"]=w.clk_x,m.extraData[y+".y"]=w.clk_y)));var D=1,k=2,A=e("meta[name=csrf-token]").attr("content"),L=e("meta[name=csrf-param]").attr("content");L&&A&&(m.extraData=m.extraData||{},m.extraData[L]=A),m.forceSync?o():setTimeout(o,10);var E,M,F,O=50,X=e.parseXML||function(e,t){return window.ActiveXObject?(t=new ActiveXObject("Microsoft.XMLDOM"),t.async="false",t.loadXML(e)):t=(new DOMParser).parseFromString(e,"text/xml"),t&&t.documentElement&&"parsererror"!=t.documentElement.nodeName?t:null},C=e.parseJSON||function(e){return window.eval("("+e+")")},_=function(t,r,a){var n=t.getResponseHeader("content-type")||"",i="xml"===r||!r&&n.indexOf("xml")>=0,o=i?t.responseXML:t.responseText;return i&&"parsererror"===o.documentElement.nodeName&&e.error&&e.error("parsererror"),a&&a.dataFilter&&(o=a.dataFilter(o,r)),"string"==typeof o&&("json"===r||!r&&n.indexOf("json")>=0?o=C(o):("script"===r||!r&&n.indexOf("javascript")>=0)&&e.globalEval(o)),o};return S}if(!this.length)return a("ajaxSubmit: skipping submit process - no element selected"),this;var u,c,l,f=this;"function"==typeof t?t={success:t}:void 0===t&&(t={}),u=t.type||this.attr2("method"),c=t.url||this.attr2("action"),l="string"==typeof c?e.trim(c):"",l=l||window.location.href||"",l&&(l=(l.match(/^([^#]+)/)||[])[1]),t=e.extend(!0,{url:l,success:e.ajaxSettings.success,type:u||e.ajaxSettings.type,iframeSrc:/^https/i.test(window.location.href||"")?"javascript:false":"about:blank"},t);var m={};if(this.trigger("form-pre-serialize",[this,t,m]),m.veto)return a("ajaxSubmit: submit vetoed via form-pre-serialize trigger"),this;if(t.beforeSerialize&&t.beforeSerialize(this,t)===!1)return a("ajaxSubmit: submit aborted via beforeSerialize callback"),this;var d=t.traditional;void 0===d&&(d=e.ajaxSettings.traditional);var p,h=[],v=this.formToArray(t.semantic,h);if(t.data&&(t.extraData=t.data,p=e.param(t.data,d)),t.beforeSubmit&&t.beforeSubmit(v,this,t)===!1)return a("ajaxSubmit: submit aborted via beforeSubmit callback"),this;if(this.trigger("form-submit-validate",[v,this,t,m]),m.veto)return a("ajaxSubmit: submit vetoed via form-submit-validate trigger"),this;var g=e.param(v,d);p&&(g=g?g+"&"+p:p),"GET"==t.type.toUpperCase()?(t.url+=(t.url.indexOf("?")>=0?"&":"?")+g,t.data=null):t.data=g;var x=[];if(t.resetForm&&x.push(function(){f.resetForm()}),t.clearForm&&x.push(function(){f.clearForm(t.includeHidden)}),!t.dataType&&t.target){var b=t.success||function(){};x.push(function(r){var a=t.replaceTarget?"replaceWith":"html";e(t.target)[a](r).each(b,arguments)})}else t.success&&x.push(t.success);if(t.success=function(e,r,a){for(var n=t.context||this,i=0,o=x.length;o>i;i++)x[i].apply(n,[e,r,a||f,f])},t.error){var y=t.error;t.error=function(e,r,a){var n=t.context||this;y.apply(n,[e,r,a,f])}}if(t.complete){var T=t.complete;t.complete=function(e,r){var a=t.context||this;T.apply(a,[e,r,f])}}var j=e("input[type=file]:enabled",this).filter(function(){return""!==e(this).val()}),w=j.length>0,S="multipart/form-data",D=f.attr("enctype")==S||f.attr("encoding")==S,k=n.fileapi&&n.formdata;a("fileAPI :"+k);var A,L=(w||D)&&!k;t.iframe!==!1&&(t.iframe||L)?t.closeKeepAlive?e.get(t.closeKeepAlive,function(){A=s(v)}):A=s(v):A=(w||D)&&k?o(v):e.ajax(t),f.removeData("jqxhr").data("jqxhr",A);for(var E=0;h.length>E;E++)h[E]=null;return this.trigger("form-submit-notify",[this,t]),this},e.fn.ajaxForm=function(n){if(n=n||{},n.delegation=n.delegation&&e.isFunction(e.fn.on),!n.delegation&&0===this.length){var i={s:this.selector,c:this.context};return!e.isReady&&i.s?(a("DOM not ready, queuing ajaxForm"),e(function(){e(i.s,i.c).ajaxForm(n)}),this):(a("terminating; zero elements found by selector"+(e.isReady?"":" (DOM not ready)")),this)}return n.delegation?(e(document).off("submit.form-plugin",this.selector,t).off("click.form-plugin",this.selector,r).on("submit.form-plugin",this.selector,n,t).on("click.form-plugin",this.selector,n,r),this):this.ajaxFormUnbind().bind("submit.form-plugin",n,t).bind("click.form-plugin",n,r)},e.fn.ajaxFormUnbind=function(){return this.unbind("submit.form-plugin click.form-plugin")},e.fn.formToArray=function(t,r){var a=[];if(0===this.length)return a;var i=this[0],o=t?i.getElementsByTagName("*"):i.elements;if(!o)return a;var s,u,c,l,f,m,d;for(s=0,m=o.length;m>s;s++)if(f=o[s],c=f.name,c&&!f.disabled)if(t&&i.clk&&"image"==f.type)i.clk==f&&(a.push({name:c,value:e(f).val(),type:f.type}),a.push({name:c+".x",value:i.clk_x},{name:c+".y",value:i.clk_y}));else if(l=e.fieldValue(f,!0),l&&l.constructor==Array)for(r&&r.push(f),u=0,d=l.length;d>u;u++)a.push({name:c,value:l[u]});else if(n.fileapi&&"file"==f.type){r&&r.push(f);var p=f.files;if(p.length)for(u=0;p.length>u;u++)a.push({name:c,value:p[u],type:f.type});else a.push({name:c,value:"",type:f.type})}else null!==l&&l!==void 0&&(r&&r.push(f),a.push({name:c,value:l,type:f.type,required:f.required}));if(!t&&i.clk){var h=e(i.clk),v=h[0];c=v.name,c&&!v.disabled&&"image"==v.type&&(a.push({name:c,value:h.val()}),a.push({name:c+".x",value:i.clk_x},{name:c+".y",value:i.clk_y}))}return a},e.fn.formSerialize=function(t){return e.param(this.formToArray(t))},e.fn.fieldSerialize=function(t){var r=[];return this.each(function(){var a=this.name;if(a){var n=e.fieldValue(this,t);if(n&&n.constructor==Array)for(var i=0,o=n.length;o>i;i++)r.push({name:a,value:n[i]});else null!==n&&n!==void 0&&r.push({name:this.name,value:n})}}),e.param(r)},e.fn.fieldValue=function(t){for(var r=[],a=0,n=this.length;n>a;a++){var i=this[a],o=e.fieldValue(i,t);null===o||void 0===o||o.constructor==Array&&!o.length||(o.constructor==Array?e.merge(r,o):r.push(o))}return r},e.fieldValue=function(t,r){var a=t.name,n=t.type,i=t.tagName.toLowerCase();if(void 0===r&&(r=!0),r&&(!a||t.disabled||"reset"==n||"button"==n||("checkbox"==n||"radio"==n)&&!t.checked||("submit"==n||"image"==n)&&t.form&&t.form.clk!=t||"select"==i&&-1==t.selectedIndex))return null;if("select"==i){var o=t.selectedIndex;if(0>o)return null;for(var s=[],u=t.options,c="select-one"==n,l=c?o+1:u.length,f=c?o:0;l>f;f++){var m=u[f];if(m.selected){var d=m.value;if(d||(d=m.attributes&&m.attributes.value&&!m.attributes.value.specified?m.text:m.value),c)return d;s.push(d)}}return s}return e(t).val()},e.fn.clearForm=function(t){return this.each(function(){e("input,select,textarea",this).clearFields(t)})},e.fn.clearFields=e.fn.clearInputs=function(t){var r=/^(?:color|date|datetime|email|month|number|password|range|search|tel|text|time|url|week)$/i;return this.each(function(){var a=this.type,n=this.tagName.toLowerCase();r.test(a)||"textarea"==n?this.value="":"checkbox"==a||"radio"==a?this.checked=!1:"select"==n?this.selectedIndex=-1:"file"==a?/MSIE/.test(navigator.userAgent)?e(this).replaceWith(e(this).clone(!0)):e(this).val(""):t&&(t===!0&&/hidden/.test(a)||"string"==typeof t&&e(this).is(t))&&(this.value="")})},e.fn.resetForm=function(){return this.each(function(){("function"==typeof this.reset||"object"==typeof this.reset&&!this.reset.nodeType)&&this.reset()})},e.fn.enable=function(e){return void 0===e&&(e=!0),this.each(function(){this.disabled=!e})},e.fn.selected=function(t){return void 0===t&&(t=!0),this.each(function(){var r=this.type;if("checkbox"==r||"radio"==r)this.checked=t;else if("option"==this.tagName.toLowerCase()){var a=e(this).parent("select");t&&a[0]&&"select-one"==a[0].type&&a.find("option").selected(!1),this.selected=t}})},e.fn.ajaxSubmit.debug=!1});
/* * @author: wanglei, yangbo * @date:13/12/17 * @name: 转换@符号*///Element.prototype.at = function(){//	var jqElement = $(this);//	var content = jqElement[0].innerHTML;//	var regexp = /((@(.+?))( |&nbsp;)|@(.+?)(<|$))/gm;//	var replaced = '';//	replaced = content.replace(regexp, //		function(matched, group1, group2, group3, //				group4, group5, group6, pos, targetStr){//			if(group3){//				return profileURL(group3)+group4;//			}//			if(group5){//				return profileURL(group5)+group6;//			}//	});//	jqElement.html(replaced);//};/**由于IE7不支持element.prototype的原型链扩展，故使用Jquery扩展*/$.fn.extend({	at:function() {		var jqElement = $(this);		var content = jqElement[0].innerHTML;		var regexp = /((@(.+?))(:|\s|&nbsp;)|@(.+?)(<|$))/gm;		var replaced = '';		replaced = content.replace(regexp, 			function(matched, group1, group2, group3, 					group4, group5, group6, pos, targetStr){				if(group3){					return profileURL(group3)+group4;				}				if(group5){					return profileURL(group5)+group6;				}		});		jqElement.html(replaced);	}});function profileURL(name){	var url = encodeURIComponent(name);	url = '/name/' + url +'/profile';	return '<a target="_blank" href="'+ url +'">@'+name +'</a>';}function isin(array, obj){	for(var rti=0 ; rti < array.length; rti++){		if(array[rti] == obj){			return true;		}	}	return false;}
var shareWindow;
var shareContent;
var shareLink;
var shareContentRemainingWordsLabel;
var shareContentRemainingWords = 0;	// 分享内容剩余字数

function checkShareContent() {
	shareContentRemainingWords =  140 - shareContent.val().length;
	shareContentRemainingWordsLabel.html("还可输入<em style='color:red;'>" +  shareContentRemainingWords + "</em>字");
}

$(document).ready(function() {
	shareWindow = $("#shareModal"); 								// 分享窗口
	shareContent = $("#shareContent"); 								// 分享内容
	shareContentRemainingWordsLabel = $("#shareContentRemainingWords"); 
	
	// 计算剩余字数
	shareContent.blur(checkShareContent);
	shareContent.focus(checkShareContent);
	shareContent.keyup(checkShareContent);
	
	$("#message_face_share").jqfaceedit({
    	txtAreaObj:$("#shareContent"),
    	containerObj:$('#emotion_icons'),
    	textareaid:"shareContent",
    	top:30,
    	left:-40
    });
	$("#sinaWeiboCheck").click(function (event, a, b) {
		var $this = $(this);
		if($this.attr("checked") == 'checked'){
			$("#tipBindInfo").html("");
		}
	});
	// 分享微博按钮事件
	$("#sharePostBtn").click(function (event, a, b) {
		if(shareContentRemainingWords < 0){
			shareContent.focus();
			return;
		}
		var sinaCheck = $("#sinaWeiboCheck").attr("checked");
		var qqCheck = $("#qqConnectCheck").attr("checked");
		if(sinaCheck != 'checked' && qqCheck != 'checked'){
			$("#tipBindInfo").html("请选择你想分享的网站");
			return;
		}
		if(shareContent.val()==""){
			shareContent.focus();
			return;
		}
		var $this = $(this);
		var par = $("#shareForm").serialize();
		$this.attr("disabled",true);
		$.post("share", par, function(result, e) {
			shareWindow.modal('hide');
			if(result.success == true){
				$.msgbox(result.desc,shareLink,{success:true});
			} else {
				$.msgbox(result.desc,shareLink,{success:false});
			}
			$this.attr("disabled",false);
		},'json');
	});
});

function bindShareClick(targetWeiboList){
	// 弹出分享窗口
	$('a[_n="share"]', targetWeiboList).click(function (event, a, b) {
		var $this = $(this);
		shareLink = $(this);
		var _postId = $this.attr("_postId");
		var _weiboLink = $this.attr("_share_link");
		
		var weiboLiDOM = $("#post_" + _postId);
		var content = $.trim($("[_n='content'][_sourceDiv]",weiboLiDOM).text()); 
		var tail = " http://www.davcai.com" + _weiboLink + " (分享自@大V彩)";
		var beyondLength = content.length + tail.length - 140;
		if(beyondLength > 0){
			content = content.substr(0,content.length-beyondLength -1) + "…";
		}
		shareContent.val(content + tail);
		shareWindow.modal('show');
		checkShareContent();
		shareContent.focus();
		
		WB_API.addAtWhoEvent($("#shareContent"));
	});
}

 /* * @author:wanglei * @date:13/12/2 * @name:实现多行省略 * *///============使用jquery扩展定义多行缩略，支持IE7浏览器=============$.fn.extend({    getText: function() {        if (this[0].innerText == undefined) {            return this[0].textContent;        } else {            return this[0].innerHTML;        }    },    setText: function(str) {         if (this[0].innerText == undefined) {            this[0].textContent = str || "";        } else {            this[0].innerHTML = str || "";        }    },    getFinalStyle: function(property, fontSize) {        var s;        if (window.getComputedStyle) {            s = window.getComputedStyle(this[0], null)[property];        } else {            s = this[0].currentStyle[property];        }        return s.substring(0, s.toString().length - 2);        },    /**	 * row	要缩略的行数	 */    mlellipsis: function(row) {        var lineHeight = null;        var element = this;        //获取计算后的样式        var fontSize = $(element).getFinalStyle("fontSize");        if (/msie/i.test(navigator.userAgent)) {            lineHeight = $(element).getFinalStyle("lineHeight", fontSize);        } else {            lineHeight = $(element).getFinalStyle("lineHeight");        }        var height = element[0].clientHeight;                if (lineHeight == "1") {            lineHeight = Number(fontSize * 1.5);            $(element).css("line-height", lineHeight + "px");        } else {            lineHeight = Number(lineHeight);        }        // 当前微博div        var $thisDiv = $(element);        var $nextDiv = $thisDiv.next();                // 处理源内容@        $($nextDiv.get(0)).at();        //若高度足够，则不用省略        var dheight = Math.floor(row * lineHeight);        var $imgs = $("img[alt='贴图']", $thisDiv);        var $newImgs = $imgs.clone(true);        var $sourceImgs = $("img[alt='贴图']", $nextDiv);        $imgs.remove();                var openClickEvent = function() {            _bandOpen();        };        if (height > dheight || $newImgs.length > 0) {            str = $(element).getText();            while (dheight < element[0].clientHeight) {                $(element).setText(str.substring(0, str.length / 2));                str = $(element).getText();            }                        var elementCont = $(element).getText();            if (height > dheight) {                elementCont = elementCont + " ...";            }            this.setText(elementCont);                        var $showSpan = $('<span class="show_div"><i></i>展开</span>').click(function() {                openClickEvent();            });                        var $hideSpan = $('<span class="hide_div"><i></i>收起</span>').click(function() {                _bandClose();            });            // 处理隐藏过的内容 @符号            $(element).at();            $thisDiv.append($showSpan);            $nextDiv.append($hideSpan);            if (height <= dheight) {                _hideExpand($thisDiv);            }            //渲染第一张缩略图            if ($newImgs.length > 0) {                var $this = $($newImgs[0]), url = $this.attr("src");                $this.attr("src", url.replace("!custom", "!thumbFile"));                $this.addClass("show-all");                $this.click(function() {                    _bandOpen();                });                $thisDiv.append($this);            }            // 查看原图            $sourceImgs.each(function() {                var $this = $(this);                var src = $this.attr("src");                src = src.substring(0, src.indexOf("!custom"));                $this.after('<a class="zoom" href="' + src + '" target="_blank" style="width:80px;"><i></i><span>查看原图</span></a>');                $this.addClass("postSourceImg");                $this.click(function() {                    _bandClose();                });            });            function _bandOpen() {                // 隐藏缩略内容                $thisDiv.hide();                // 显示原始内容                $nextDiv.show();            }            ;            function _bandClose() {                // 显示缩略内容                $thisDiv.show();                // 隐藏原始内容                $nextDiv.hide();            }            ;        } else {            // 处理隐藏过的内容 @符号            $(element).at();        }    }});function _hideExpand($thisDiv) {    $thisDiv.find(".show_div").hide();}
/**
 * author : Yang Bo (bob.yang.dev@gmail.com)
 **/

// debug function, 上线前需要注释掉，否则IE8以下报错。
function log(msg){
//	console.log(msg);
}

// ========= global =============
g_betList= new Array();				// 投注列表
g_currentBet = new SSQBet();			// 当前选中的投注内容
g_countDownTimer = null;			// 倒计时的计时器
g_issueInfo = null;				// 期信息

// 胆拖投注用
g_currentBetDT = new SSQBetDT();		// 当前选中的投注内容 

// ========= constants ==========
DISTANCE_DAY = 24*60*60*1000;			// 天
DISTANCE_MINUTE = 60*1000;			// 分钟
DISTANCE_SECOND = 1000;				// 秒

// ========= 普通投注页面JS ============
function init_ball_click_handler(){
	// 红球
	iterate_red_balls(function(ball){
		ball.click(function(){
			$(this).toggleClass('red_ball');
			if(count_red_balls()>16){
				alert("红球最多只能选16个");
				$(this).toggleClass('red_ball');
				return;
			}
			update_sel_prompt();
		});
	});
	// 蓝球
	iterate_blue_balls(function(ball){
		ball.click(function(){
			$(this).toggleClass('blue_ball');
			update_sel_prompt();
		});
	});
}

// 遍历红球元素
function iterate_red_balls(fn_callback){
	for(var i=1; i<=33; i++){
		var ball_id = "#rb"+i;
		fn_callback($(ball_id));
	}
}

// 遍历蓝球元素
function iterate_blue_balls(fn_callback, css_selector){
	for(var i=1; i<=16; i++){
		var ball_id = "#bb"+i;
		if(css_selector){
			ball_id = (css_selector + i);
		}
		fn_callback($(ball_id));
	}
}

// 更新选择多少红球、蓝球的提示
function update_sel_prompt(){
	var red_ball_num = count_red_balls();
	var blue_ball_num = count_blue_balls();
	$('#prompt>.red_text').html(red_ball_num);
	$('#prompt>.blue_text').html(blue_ball_num);
	// 计算注数
	update_bet_object();
	$('#notes').html(g_currentBet.notes);
	$('#money').html(g_currentBet.money);
}

function dt_update_sel_prompt(){
	dt_update_bet_object();
	var red_ball_num = g_currentBetDT.countRedBalls();
	var blue_ball_num = g_currentBetDT.countBlueBalls();
	$('#prompt_dt>.red_text').html(red_ball_num);
	$('#prompt_dt>.blue_text').html(blue_ball_num);
	// 计算注数
	$('#notes').html(g_currentBetDT.notes);
	$('#money').html(g_currentBetDT.money);
}

// 根据选择的红球、蓝球信息更新全局投注对象。
function update_bet_object(){
	var rb = g_currentBet.redBalls;
	var bb = g_currentBet.blueBalls;
	rb.splice(0, rb.length);
	bb.splice(0, bb.length);
	iterate_red_balls(function(ball){
		var ballId = getBallId(ball);
		if (ball.hasClass('red_ball')){
			rb.push(parseInt(ballId));
		}
	});
	iterate_blue_balls(function(ball){
		var ballId = getBallId(ball);
		if (ball.hasClass('blue_ball')){
			bb.push(parseInt(ballId));
		}
	});
	g_currentBet.resolve();
}

// 计算有多少红球被选中了
function count_red_balls(){
   var num = 0;
   for(var i=1; i<=33; i++){
    var redball_id = "rb"+i;
    if ($("#"+redball_id).hasClass('red_ball')){
       num++;
    }
  }
log('红球:'+num);
  return num;
}

// 计算有多少蓝球被选中了
function count_blue_balls(){
   var num = 0;
   for(var i=1; i<=16; i++){
    var ball_id = "bb"+i;
    if ($("#"+ball_id).hasClass('blue_ball')){
       num++;
    }
  }
  return num;
}

// 添加"机选红球"处理方法
function init_random_sel_handler(){
	$('#random_sel_red_btn').click(function(){
		// 机选红球
		clear_red_balls();
		var red_random_num = $('#random_red_select_id').val();
		var sel_num = parseInt(red_random_num);
		while(count_red_balls()<sel_num){
			var idx = Math.ceil(Math.random()*33);
			var ball = $('#'+'rb'+idx);
			ball.removeClass('red_ball');
			ball.addClass('red_ball');
		}
		update_sel_prompt();
	});
	$('#random_sel_blue_btn').click(function(){
		// 机选蓝球
		clear_blue_balls();
		var blue_random_num = $('#random_blue_select_id').val();
		var sel_num = parseInt(blue_random_num);
		while(count_blue_balls()<sel_num){
			var idx = Math.ceil(Math.random()*16);
			var ball = $('#'+'bb'+idx);
			ball.removeClass('blue_ball');
			ball.addClass('blue_ball');
		}
		update_sel_prompt();
	});
}

// 清除红球的被选中状态
function clear_red_balls(){
	iterate_red_balls(function(ball){
		ball.removeClass('red_ball');
	});
}

// 清除蓝球的被选中状态
function clear_blue_balls(){
	iterate_blue_balls(function(ball){
		ball.removeClass('blue_ball');
	});
}

function init_clear_nums(){
	$('#prompt a').click(function(){
		clear_red_balls();
		clear_blue_balls();
		update_sel_prompt();
		return false;
	});
}

// 添加投注到列表的 handler
function init_add_bet_list(){
	$('#add_list_btn').click(function(){
		update_bet_object();
		// 只加入有效选择投注，无效投注不处理
		if (g_currentBet.notes == 0){
			return;
		}
		g_betList.unshift(g_currentBet);
		g_currentBet = new SSQBet();
		update_bet_list_ui();
		clear_red_balls();
		clear_blue_balls();
		update_sel_prompt();
		update_scheme_prompt();
	});
}

// 渲染 g_betList 的内容
function update_bet_list_ui(){
	var rows = create_rows();
	$('#scheme_list').html(rows);
	// 鼠标悬浮效果
	$('.bet_scheme_row').hover(function(){$(this).css('backgroundColor','#E4F4FC');}, 
		function(){$(this).css('backgroundColor', 'white');});
	// 添加删除小图标 handler
	$('.bet_scheme_row span img').click(delete_row);
	init_bet_click_handler();
}

// 胆拖页面，用 g_betList 渲染投注方案列表框
function dt_update_bet_list_ui(){
	var rows = dt_create_rows();
	$('#scheme_list').html(rows);
	// 鼠标悬浮效果
	$('.bet_scheme_row').hover(function(){$(this).css('backgroundColor','#E4F4FC');}, 
		function(){$(this).css('backgroundColor', 'white');});
	// 添加删除小图标 handler
	$('.bet_scheme_row span img').click(dt_delete_row);
	dt_init_bet_click_handler();
}

function create_rows(){
	var rows = '';
	log('bet list: ' + g_betList.length);
	for(var i=0; i<g_betList.length; i++){
		var bet = g_betList[i];
		rows +=
		'<div class="bet_scheme_row">'+
		'	<span><img title="删除" src="http://trade.davcai.com/df/images/delete.gif" betidx="'+i+'"></span>'+
		'	<div class="scheme_row_content">'+
		'	<font color="#E1401E">红</font>：'+ format_nums(bet.redBalls).join(' ') +
		' + <font color="#4F87E3">蓝</font>：'+ format_nums(bet.blueBalls).join(' ')+' ['+bet.notes+'注 '+bet.money+'元]'+
		'	</div>'+
		'</div>';
	}
	return rows;
}

function dt_create_rows(){
	var rows = '';
	log('bet list: ' + g_betList.length);
	for(var i=0; i<g_betList.length; i++){
		var bet = g_betList[i];
		var danStr = '';
		if (bet.redDanBalls.length > 0){
			danStr = '('+ format_nums(bet.redDanBalls).join(' ') +')';
		}
		rows +=
		'<div class="bet_scheme_row">'+
		'	<span><img title="删除" src="http://trade.davcai.com/df/images/delete.gif" betidx="'+i+'"></span>'+
		'	<div class="scheme_row_content">'+
		'	<font color="#E1401E">红</font>：'+ danStr + format_nums(bet.redTuoBalls).join(' ') +
		' + <font color="#4F87E3">蓝</font>：'+ format_nums(bet.blueBalls).join(' ')+' ['+bet.notes+'注 '+bet.money+'元]'+
		'	</div>'+
		'</div>';
	}
	return rows;
}

// 格式化数字为01,02形式。输入、输出为数组。
function format_nums(nums){
	var fn = [];
	for(var i=0; i<nums.length; i++){
		var n = format_one_num(nums[i]);
		fn.push(n);
	}
	return fn;
}

function format_one_num(num){
	var n = num < 10 ? '0'+num : num;
	return n;
}

function delete_row(){
	var idx = parseInt($(this).attr('betidx'));
	g_betList.splice(idx,1);
	update_bet_list_ui();
	update_scheme_prompt();
}

function dt_delete_row(){
	var idx = parseInt($(this).attr('betidx'));
	g_betList.splice(idx,1);
	dt_update_bet_list_ui();
	update_scheme_prompt();
}

// 随机投注按钮处理
function init_random_bet(){
	$('.random_white_btn').click(function(){
		var txt = $(this).html();
		var match = /机选(.+)注/.exec(txt);
		if (!match) return;
		var cnNum = match[1];
		switch(cnNum){
			case '一': add_random_bet(1); break;
			case '五': add_random_bet(5); break;
			case '十': add_random_bet(10); break;
			default: break;
		}
		update_scheme_prompt();
	});
}

function dt_init_random_bet(){
	$('.random_white_btn').click(function(){
		var txt = $(this).html();
		var match = /机选(.+)注/.exec(txt);
		if (!match) return;
		var cnNum = match[1];
		switch(cnNum){
			case '一': dt_add_random_bet(1); break;
			case '五': dt_add_random_bet(5); break;
			case '十': dt_add_random_bet(10); break;
			default: break;
		}
		update_scheme_prompt();
	});
}

function add_random_bet(num){
	for(var i=0; i<num; i++){
		var bet = new SSQBet();
		bet.redBalls = random_balls(33, 6);
		bet.blueBalls = random_balls(13, 1);
		bet.resolve();
		g_betList.push(bet);
	}
	update_bet_list_ui();
}

function dt_add_random_bet(num){
	for(var i=0; i<num; i++){
		var bet = new SSQBetDT();
		bet.redTuoBalls = random_balls(33, 6);
		bet.blueBalls = random_balls(13, 1);
		bet.resolve();
		g_betList.push(bet);
	}
	dt_update_bet_list_ui();
}

// 随机生成 count 个数，数值范围在[0, max]之间
function random_balls(max, count){
	var ret = [];
	while(ret.length<count){
		var idx = Math.ceil(Math.random()*max);
		if (array_index(ret, idx) < 0){
			ret.push(idx);
		}
	}
	return ret.sort(sortNumber);
}

function sortNumber(a,b) {
	return a - b;
}

// 搜索 array 是否包含 target, 返回 target 的下标。
function array_index(array, target){
	for(var i=0; i<array.length; i++){
		if(array[i]==target) return i;
	}
	return -1;
}

// 清空列表
function init_clear_list(){
	$('.clear_list_btn').click(function(){
		g_betList.splice(0, g_betList.length);
		update_bet_list_ui();
		update_scheme_prompt();
	});
}

function dt_init_clear_list(){
	$('.clear_list_btn').click(function(){
		g_betList.splice(0, g_betList.length);
		dt_update_bet_list_ui();
		update_scheme_prompt();
	});
}

// 投注倍数，方案信息提示内容
function init_bet_multiple(){
	$('#minus_btn').click(function(){
		var m = getSchemeMultiple();
		if (m>1) {
			m -= 1;
			$('#multiple_text').val(m);
			update_scheme_prompt();
		}
	});
	$('#plus_btn').click(function(){
		var m = getSchemeMultiple();
		if (m<99) {
			m += 1;
			$('#multiple_text').val(m);
			update_scheme_prompt();
		}
	});
	// 限制倍数 input text field 只能输入99以下的数字
	$('#multiple_text').keyup(function(event){
		log('text: '+$(this).val());
		var m = $(this).val();
		if (/^\d+$/.test(m) ){
			var num = parseInt(m);
			if (num>99) $(this).val(99);
			if (num<=0) $(this).val(1);
		}else{
			$(this).val(1);
		}
		update_scheme_prompt();
	});
}

// 更新方案注数、金额信息
function update_scheme_prompt(){
	// 累加所有投注
	var totalNotes = 0;
	for (var i=0; i<g_betList.length; i++){
		var bet = g_betList[i];
		totalNotes += bet.notes;
	}
	var multiple = getSchemeMultiple();
	var totalMoney = multiple * totalNotes * 2;
	// 更新UI
	$('#scheme_notes').html(totalNotes);
	$('#scheme_money').html(totalMoney);
}

// 获取倍数值
function getSchemeMultiple(){
	return parseInt($('#multiple_text').val());
}

// 投注项点击事件处理函数
function init_bet_click_handler(){
	$('.bet_scheme_row').click(function(){
		var index = $(this).find('span img').attr('betidx');
		var bet = g_betList[parseInt(index)];
		select_balls_by(bet);
	});
}

// 胆拖页面，投注项点击事件处理函数
function dt_init_bet_click_handler(){
	$('.bet_scheme_row').click(function(){
		var index = $(this).find('span img').attr('betidx');
		var bet = g_betList[parseInt(index)];
		dt_select_balls_by(bet);
	});
}

// 用一个投注对象设置号码选择块的信息。
function select_balls_by(bet){
	clear_red_balls();
	clear_blue_balls();
	select_balls(bet.redBalls, '#rb', 'red_ball');
	select_balls(bet.blueBalls, '#bb', 'blue_ball');
	update_sel_prompt();
}

// 胆拖页面，用一个投注对象设置号码选择块的信息。
function dt_select_balls_by(bet){
	clear_red_dan_balls();
	clear_red_tuo_balls();
	clear_blue_balls();
	select_balls(bet.redDanBalls, '#dan_balls #rb', 'red_ball');
	select_balls(bet.redTuoBalls, '#tuo_balls #tuo_rb', 'red_ball');
	select_balls(bet.blueBalls, '#bb', 'blue_ball');
	dt_update_sel_prompt();
}

// 帮助方法,更新号码选择区球的选中状态
function select_balls(balls, ballIdPrefix, ballClass){
	for(var i=0; i<balls.length; i++){
		$(ballIdPrefix+balls[i]).addClass(ballClass);
	}
}

// 验证投注方案，提交投注表单信息
function init_bet_confirm(){
	$('#confirm_buy_form input:image').click(function(){
		if(!validate_scheme()){
			alert("请先选择投注号码！");
			return false;
		}
		insert_form_fields();
		$('#confirm_buy_form').submit();
	});
}

// 验证方案信息是否合法
function validate_scheme(){
	return g_betList.length > 0;
}

// 序列化form提交需要的hidden fields内容
function insert_form_fields(){
	$('#confirm_buy_form input[name=scheme]').val(encode_scheme());
	$('#confirm_buy_form input[name=issue]').val(g_issueInfo.issueNumber);
	$('#confirm_buy_form input[name=multiple]').val($('#multiple_text').val());
	//$('#confirm_buy_form input[name=buy_type]').val($('.row_content input[name=buy_type]:checked').val());
	//$('#confirm_buy_form input[name=secret_type]').val($('.row_content input[name=secret_type]:checked').val());
}

// 编码投注方案内容。
// 双色球前后台参数定义:
// 单式投注:  SSQ_DS  格式：01,02,16,19,27,31|01; 04,08,16,19,27,31|16;
// 复式投注:  SSQ_FS  格式：01,02,03,16,19,21,23,25,33|01,09
// 胆拖投注:  SSQ_DT  格式：01,02,03@04,05,06,07,08|01,09, 胆码最少1个,最多5个,拖码最多20个,必须是复式方案
function encode_scheme(){
	var encoded = "";
	for(var i=0; i<g_betList.length; i++){
		var bet = g_betList[i];
		if (bet.type == 'dantuo'){
			var dan = '';
			if (bet.redDanBalls.length>0){
				dan = format_nums(bet.redDanBalls).join(',');
				if(bet.redDanBalls.length+bet.redTuoBalls.length==6){
					dan += ",";	// 胆加拖只有6个数字，做成单式投注
				}else{
					dan += "@";
				}
			}
			var tuo = format_nums(bet.redTuoBalls).join(',');
			var bb = format_nums(bet.blueBalls).join(',');
			encoded += dan + tuo + '|' + bb + ';';
		}else{
			var rb = format_nums(bet.redBalls);
			var bb = format_nums(bet.blueBalls);
			encoded += rb.join(',') + '|' + bb.join(',') + ';';
		}
	}
	return encoded;
}


// ========= 双色球投注类 =========
function SSQBet(){
	this.type = 'normal';	// 普通投注
	// member properties
	this.redBalls = new Array();
	this.blueBalls = new Array();
	this.notes = 0;	// 注数
	this.money = 0; // 投注的总金额

	// member functions
	
	// 计算注数和钱数
	this.resolve = function(){
		var redBallNum = this.redBalls.length;
		var blueBallNum = this.blueBalls.length;
		log('红球:'+this.redBalls);
		log('蓝球:'+this.blueBalls);
		this.notes = combination(redBallNum, 6) * blueBallNum;
		this.money = this.notes * 2;	// 一注2元
	};
}

// 更新倒计时信息
function updateCountDownInfo(){
	var target = parseLocalTime(g_issueInfo.stopTimeForUser);
	var countDownDays = distanceTo(target, DISTANCE_DAY);
	var countDownTime = distanceToIgnoreDays(target);
	$('#remain_time_box').html(countDownDays+"天 "+
		format_one_num(countDownTime.getHours())+":"+
		format_one_num(countDownTime.getMinutes())+":"+
		format_one_num(countDownTime.getSeconds()));
}

// 倒计时 timer 函数
function countDownTimeout(){
	updateCountDownInfo();
	g_countDownTimer = setTimeout("countDownTimeout()", 1000);
}

// ========== 工具方法 ==========
//
// 计算组合数，从m中取n个数的组合数。
function combination(m, n){
	if (n<=0 || m<=0 || m<n) return 0;
	if (n > m - n) {
		n = m - n;
	}
	var cbn = 1;
	for (var i = m; i >= m - n + 1; i--) {
		cbn = cbn * i;
	}
	for (var i = n; i > 1; i--) {
		cbn = cbn/i;
	}
	return cbn;
}

// 调整时区
// timezoneOffset 要减掉的分钟, 如:GMT+0800 (中国标准时间) 是-480
function adjustTimeZone(time, timezoneOffset) {
	var t = time.getTime() + timezoneOffset*60*1000;
	return new Date(t);
}

// 用GMT+0800 (中国标准时间) 解析服务器传来的时间串 time_str.
function parseLocalTime(time_str) {
	if (! /.*\+\d{4}/.test(time_str)){
		time_str += "+0000";
	}
	var d = new Date(time_str);
	if (!d){
		d = parseDateForIE(time_str);
	}
	return adjustTimeZone(d, -480);
}

// 计算当前时间距离指定时间还有几天
// targetTime: 目标时间
// unit: 单位，可以是 DISTANCE_DAY, DISTANCE_MINUTE
function distanceTo(targetTime, unit){
	var now = new Date();
	var diff = Math.floor((targetTime - now.getTime() ) / unit);
	return diff;
}

// 计算当前时间距离指定 targetTime 时间还有多少秒，舍弃整数天。
// return: Date 对象，它的时、分、秒是距离target的时间
function distanceToIgnoreDays(targetTime, unit) {
	var now = new Date();
	var origin = new Date("1970/1/1 00:00:00");
	var diffTime = targetTime - now.getTime();
	var distance = new Date(origin.getTime()+diffTime);
	return distance;
}


var g_sysSecond;
var g_interValObj;

function startCountDown(countDownSeconds){
    g_sysSecond = parseInt(countDownSeconds); //这里获取倒计时的起始时间 
    g_interValObj = window.setInterval(setRemainTime, 1000); //间隔函数，1秒执行 
}

//将时间减去1秒，计算天、时、分、秒 
function setRemainTime() {
    if (g_sysSecond > 0) {
	g_sysSecond = g_sysSecond - 1;
	var second = Math.floor(g_sysSecond % 60);             // 计算秒     
	var minute = Math.floor((g_sysSecond / 60) % 60);      //计算分 
	var hour = Math.floor((g_sysSecond / 3600) % 24);      //计算小时 
	var day = Math.floor((g_sysSecond / 3600) / 24);       //计算天 
	$('#remain_time_box').html(day+"天 "+
		format_one_num(hour)+":"+
		format_one_num(minute)+":"+
		format_one_num(second));
    } else { //剩余时间小于或等于0的时候，就停止间隔函数 
	window.clearInterval(g_interValObj);
    }
} 


// ============== ajax ==============

// 获取期信息
// return IssueInfo obj.
function aj_ssq_issue_info(){
	$.ajax({
		url: 'http://trade.davcai.com/df/ssq/aj_issue.do?jsonp=?',
		cache: false,
		crossDomain: true,
		dataType: 'jsonp'
	}).done(function(data){
		log(data);
		if(!data.success){
			log("获取期信息失败！");
			return;
		}
		g_issueInfo = data.issue;
		countDown = data.countDownSeconds;
		$('#saling_text span').html(g_issueInfo.issueNumber);
		// 解析倒计时
		//updateCountDownInfo();
		//countDownTimeout();
		log('投注截止时间：'+g_issueInfo.stopTimeForUser);
		startCountDown(countDown);
		
		//构造双色球期信息内容
		compose_ssq_curr_issue_info(g_issueInfo.issueNumber);
	});
}

var compose_ssq_curr_issue_info = function(issueNumber) {
	var issueTpl = 
    '<li class="issue-ssq">' +
	    '<div class="sz-lottery-logo"><img src="images/lottery/ssq.png"/></div>' +
	    '<div id="saling_text" class="sz-lottery-curr">' +
			'双色球第<span class="red_text sz-higher-blue">{0}</span>期<br/>' +
			'距离截止投注还有：<span id="remain_time_box" class="sz-higher-red"></span>' +
		'</div>' +
		'<div class="pre-issue-info">' +
			'<span class="ssq-pre-issue">上期开奖号码：' +
			'</span>' +
			'<span>奖池滚存：<span class="sz-higher-red jackpot"></span></span>' +
			'<input type="button" id="sz_bet_btn" value="投注"/>' +
		'</div>' +
	'</li>';
	
	var issue = replacePlaceHolder(issueTpl, [issueNumber]);
	
	$("#match_sz").find("ul").append($(issue));
	$("#match_sz").find("#sz_bet_btn").click(function() {
		var ssqUrl = "http://trade.davcai.com/df/bet/ssq_general.html";
		document.location.href = ssqUrl;
	});
	aj_ssq_results($("#match_sz"));
};

// 获取双色球结果
function aj_ssq_results(wrapper){
	$.ajax({
		url: 'http://trade.davcai.com/df/ssq/aj_results.do?max=1&jsonp=?',
		cache: false,
		crossDomain: true,
		dataType: 'jsonp'
	}).done(function(results){
		if(results && results.length > 0) {
			var rs = results[0];
			var tpl = '<span class="sz-higher-red">{0}&nbsp;</span>' +
				'<span class="sz-higher-blue">{1}</span>'; 
			var preIssue = replacePlaceHolder(tpl, [rs.redBalls, rs.blueBall]);
			wrapper.find(".ssq-pre-issue").append($(preIssue));
			
			//奖池
			if(rs.jackpot) {
				var jackpot = addCommas(rs.jackpot);
				$(".jackpot").text(jackpot+"元");
			}
		}
	});
}

function addCommas(nStr) {
	nStr += '';
	x = nStr.split('.');
	x1 = x[0];
	x2 = x.length > 1 ? '.' + x[1] : '';
	var rgx = /(\d+)(\d{3})/;
	while (rgx.test(x1)) {
		x1 = x1.replace(rgx, '$1' + ',' + '$2');
	}
	return x1 + x2;
}

// 构造双色球结果行html.
// ssq_result 对应 SSQResult class.
function compose_result_row(ssq_result){
	var html = '<div class="board_row orange_text">'+
            '<span class="issue">'+ssq_result.shortIssueNum+'</span>'+
            '<span class="result"><span class="orange_text">'+ssq_result.redBalls+
	    ' <span class="blue_text">'+ssq_result.blueBall+'</span></span></span>'+
            '<span class="more_or_less">'+ssq_result.bigSmall+'</span>'+
            '<span class="sum">'+ssq_result.sum+'</span>'+
	'</div>';
	return html;
}

// =============== 胆拖投注 =================

// 胆拖区小球被点击时的事件响应
function dt_init_ball_click_handler(){
	// 红球胆区
	iterate_red_dan_balls(function(ball){
		ball.click(function(){
			var ball = $(this);
			ball.toggleClass('red_ball');
			dt_balls_change();
			if(g_currentBetDT.redDanBalls.length>5){
				ball.toggleClass('red_ball');
				dt_balls_change();
				alert("最多只能选择5个胆码");
				return;
			}
			dt_exclude(ball, 'tuo');
		});
	});
	// 红球拖区
	iterate_red_tuo_balls(function(ball){
		ball.click(function(){
			log('拖区被点击');
			var ball = $(this);
			ball.toggleClass('red_ball');
			dt_balls_change();
			if(g_currentBetDT.redTuoBalls.length>20){
				ball.toggleClass('red_ball');
				dt_balls_change();
				alert("最多只能选择20个拖码");
				return;
			}
			dt_exclude(ball, 'dan');
		});
	});
	// 蓝球
	iterate_blue_balls(function(ball){
		ball.click(function(){
			$(this).toggleClass('blue_ball');
			dt_balls_change();
		});
	}, "#blue_ball_dt #bb");
}

// 把 beExcludedType 中对应ball的序号的小球取消选中状态。
// beExcludedType 取值：dan,tuo 
function dt_exclude(ball, beExcludedType){
	var ballId = getBallId(ball);
	if (beExcludedType == 'dan'){
		log('dan excluded.'+ballId);
		$('#dan_balls #rb'+ballId).removeClass('red_ball');
	}else{
		log('tuo excluded.'+ballId);
		$('#tuo_balls #tuo_rb'+ballId).removeClass('red_ball');
	}
}

function iterate_red_dan_balls(fn_callback){
	for(var i=1; i<=33; i++){
		var ball_id = "#dan_balls #rb"+i;
		fn_callback($(ball_id));
	}
}

function iterate_red_tuo_balls(fn_callback){
	for(var i=1; i<=33; i++){
		var ball_id = "#tuo_balls #tuo_rb"+i;
		fn_callback($(ball_id));
	}
}

// 清空号码
function dt_init_clear_nums(){
	$('#prompt_dt a').click(function(){
		clear_red_dan_balls();
		clear_red_tuo_balls();
		clear_blue_balls();
		dt_balls_change();
		return false;
	});
}

function clear_red_dan_balls(){
	iterate_red_dan_balls(function(ball){
		ball.removeClass('red_ball');
	});
}

function clear_red_tuo_balls(){
	iterate_red_tuo_balls(function(ball){
		ball.removeClass('red_ball');
	});
}

// 确认选号按钮点击事件
function dt_init_add_bet_list(){
	$('#add_list_btn_dt').click(function(){
		dt_update_bet_object();
		// 只加入有效选择投注，无效投注不处理
		if (g_currentBetDT.notes == 0){
			return;
		}
		g_betList.unshift(g_currentBetDT);
		g_currentBetDT = new SSQBetDT();
		dt_update_bet_list_ui();
		clear_red_dan_balls();
		clear_red_tuo_balls();
		clear_blue_balls();
		dt_update_sel_prompt();
		update_scheme_prompt();
	});
}


// 用户点击了小球 
function dt_balls_change(){
	dt_update_bet_object();
	$('#prompt_dt>.red_text').html(g_currentBetDT.redDanBalls.length+g_currentBetDT.redTuoBalls.length);
	$('#prompt_dt>.blue_text').html(g_currentBetDT.blueBalls.length);
	// 计算注数
	$('#notes').html(g_currentBetDT.notes);
	$('#money').html(g_currentBetDT.money);
}

// 更新 g_currentBetDT
function dt_update_bet_object(){
	var danball = g_currentBetDT.redDanBalls;
	var tuoball = g_currentBetDT.redTuoBalls;
	var bb = g_currentBetDT.blueBalls;
	danball.splice(0, danball.length);
	tuoball.splice(0, tuoball.length);
	bb.splice(0, bb.length);
	iterate_red_dan_balls(function(ball){
		var ballId = getBallId(ball);
		if (ball.hasClass('red_ball')){
			danball.push(parseInt(ballId));
		}
	});
	iterate_red_tuo_balls(function(ball){
		var ballId = getBallId(ball);
		if (ball.hasClass('red_ball')){
			tuoball.push(parseInt(ballId));
		}
	});
	iterate_blue_balls(function(ball){
		var ballId = getBallId(ball);
		if (ball.hasClass('blue_ball')){
			bb.push(parseInt(ballId));
		}
	}, "#blue_ball_dt #bb");
	g_currentBetDT.resolve();
}

function getBallId(ballElement){
	var pattern = /.*(rb|bb)(\d+)/;
	var str = ballElement.attr('id');
	var result = pattern.exec(str);
	if (result){
		return result[2];
	}
	log('Can not get ball id! ' + str);
}

function count_red_dan_balls(){
   var num = 0;
   for(var i=1; i<=33; i++){
    var redball_id = "rb"+i;
    if ($("#dan_balls #"+redball_id).hasClass('red_ball')){
       num++;
    }
  }
  return num;
}

function count_red_tuo_balls(){
   var num = 0;
   for(var i=1; i<=33; i++){
    var redball_id = "tuo_rb"+i;
    if ($("#tuo_balls #"+redball_id).hasClass('red_ball')){
       num++;
    }
  }
  return num;
}

// ========= 双色球胆拖投注类 =========
function SSQBetDT(){
	this.type = 'dantuo';
	// member properties
	this.redDanBalls = new Array();
	this.redTuoBalls = new Array();
	this.blueBalls = new Array();
	this.notes = 0;	// 注数
	this.money = 0; // 投注的总金额

	// member functions
	
	// 计算注数和钱数
	this.resolve = function(){
		var danBallNum = this.redDanBalls.length;
		var tuoBallNum = this.redTuoBalls.length;
		var blueBallNum = this.blueBalls.length;
		this.notes = combination(tuoBallNum, 6-danBallNum) * blueBallNum;
		this.money = this.notes * 2;	// 一注2元
		log('resolving: ' + danBallNum + ", " + tuoBallNum + ", " + blueBallNum+ ", "+ this.notes);
	};
	this.countRedBalls = function(){
		return this.redDanBalls.length + this.redTuoBalls.length;
	};
	this.countBlueBalls = function(){
		return this.blueBalls.length;
	};
}

/*
 * vim: set ts=4
 */

// ============== ajax ==============
// 获取JX11期信息
var aj_jx11_issue_info = function() {
	$.ajax({
		url : 'http://trade.davcai.com/df/ssq/aj_issue.do?jsonp=?',
		cache : false,
		crossDomain : true,
		dataType : 'jsonp'
	}).done(function(data) {

	});
};
// 模板
var commentPostAreaTemplate = 
'<div class="commentPostArea">'+
	'<div class="arrow-top">'+
		'<span class="arrow-top-out"></span>'+
		'<span class="arrow-top-in"></span>'+
	'</div>'+
	'<div class="inputArea">'+
		'<textarea class="commentEditor"'+
			'style="min-height: 50px; height: 60.21875px;font-family:Microsoft YaHei;font-size:13px;"></textarea>'+
	'</div>'+
	'<p class="forbidden" style="display: none">由于用户的设置，你不能对主帖进行评论</p>'+
	'<span class="showFaceButton">&nbsp;</span>'+
	'<span class="addStock">&nbsp;</span>'+
	
	
	'<div class="editor fn-btn">'+
		'<div class="emotion selector">'+
			'<span title="插入表情" class="face" comment_face><s></s><span>表情</span></span>'+
			'<span title="过滤赛事" class="match matchFindForward game_show_btn"><s></s><span>赛事</span></span>'+
		'</div>'+
	'</div>'+
	
	'<label class="forward-home"><input type="checkbox" name="forward"> 同时转发到我的首页 </label>'+
	'<form class="replyForm" action="" method="get">'+
		'<input type="submit" class="commentSubmit button" value="发布">'+
	'</form>'+
	'<div class="fixit"></div>'+
'</div>';

// 使用非入侵模式安装“评论”连接的点击事件，并显示评论框
function onComment(commentLink){
	var commentContainer = commentLink.closest('li').find('.commentContainer');
	if(0 != commentContainer.length) {
		commentContainer.toggle();
		// 自动调整箭头位置
		adjustArrowPos(commentContainer, commentLink);
	} else {
		showComments(commentLink);
	}
	
	//点击评论链接，隐藏跟单form
	var followFormDiv = commentLink.closest('li').find('[weiboBetFormDiv]');
	if(followFormDiv){
		followFormDiv.hide();
	}
}

function validateCommentText(commentText){
	if ($.isBlankStr(commentText)) {
		$.msgBox('请输入内容后再提交。', null, 
				{pos:'center', success: false});
		return false;
	}
	if (commentText.length>600){
		$.msgBox('评论字数超长，请节约用字，不要超过600字。', null, 
				{pos:'center', success: false});
		return false;
	}
	return true;
}

function showComments(commentLink){
	// text area id 不能重复，否则会出现内容重复追加的bug
	var postId = commentLink.attr('_postid');
	var post = commentLink.closest('li');
	
	var posi = commentLink.closest('.content.status_content');
	
	var commentContainer =
	$('<div class="commentContainer display_textarea" style="display: block;">'+
		commentPostAreaTemplate +
		'<div class="loading" style="display: none;"></div>'+
	'</div>');
	
	//post.append(commentContainer);
	posi.after(commentContainer);
	
	var commentTextArea = commentContainer.find("textarea");
	bindWeiboButtonEvent(commentTextArea);
	
	// 自动调整箭头位置
	adjustArrowPos(commentContainer, commentLink);
	var commentsLoading = post.find('.loading');

	// 评论发布按钮事件绑定
	var form = commentContainer.find('form');
	form.submit(function(event){
		event.preventDefault();
		var textarea = commentContainer.find('textarea');
		if (textarea.val()==''){
			return;
		}
		var commentText = textarea.val();
		if (!validateCommentText(commentText)){
			return;
		}
		commentsLoading.show();
		$.ajax('http://www.davcai.com/post_cmnt', {
			dataType: 'jsonp',
			method: 'post',
			cache: false,
			data: {
				pid: postId,
				forward: commentContainer.find('input:checkbox').is(':checked'),
				comment: textarea.val()
			},
			success: function(result){
				updateCmtCountInWeiboMeta(commentContainer,1);
				loadComments(postId, commentsLoading, post);
			},
			error: function(){
				commentsLoading.hide();
				$.msgBox('抱歉，因网络问题操作失败。', null, {pos:'center', success: false});
				//alert('抱歉，因网络问题操作失败。');
			}
		});
		textarea.val('');
	});
	// 加载历史评论
	commentsLoading.show();
	loadComments(postId, commentsLoading, post);
}

function adjustArrowPos(commentContainer, commentLink){
	var linkLeft = commentLink.offset().left;
	var textAreaLeft = commentContainer.find('.commentPostArea').offset().left;
	var left = linkLeft - textAreaLeft + commentLink.width()/2;
	commentContainer.find('.arrow-top-out,.arrow-top-in').css({'left':left});
}

function loadComments(postId, commentsLoading, post){
	// 最后一个li需要加上 "last" class
	var comments =
	'<div class="commentInfo" style="">'+
		'<div class="hd">'+
//			'<span class="sort"><span>排序：</span>'+
//			'<a href="javascript:;" sort="false" class="active" title="最近">最近</a>'+
//			'<a href="javascript:;" sort="true" title="最早">最早</a>'+
//			'<a href="javascript:;" sort="like" title="赞" class="last">赞</a>'+
//			'</span>'+
			'<span class="commentNum">全部评论<span class="counts">（{{commentCount}}）</span></span>'+
		'</div>'+
		'<div class="loading" style="display: none;"></div>'+
		'<ul class="commentList">{{#comments}}'+
			'<li id="comment_{{id}}" _comment="{{id}}">'+
				'<div class="headpic" _userid="{{author.weiboUserId}}">'+
					'<a href="{{authorHomeURL}}" data-name="{{author.nickName}}">'+
					'<img width="30px" height="30px" src="{{author.headImageURL}}">'+
					'</a>'+
				'</div>'+
				'<div class="content" style="display: block;">'+
					'<div class="comment">'+
						'<a href="{{authorHomeURL}}" data-name="{{author.nickName}}">{{author.nickName}}<span '+
							'class="user_remark" data-name="{{author.nickName}}" style="display: none">()</span>'+
							'</a>{{{replyTo}}}'+
					'</div>'+
					'<div class="cmt_con summary">'+
						'<span>{{content}}</span>'+
					'</div>'+
					'<div class="ops">'+
						'<div class="infos">'+
							'<span class="createAt" createdat="{{createTimeLong}}">{{createTimeSpan}}</span><a'+
								'href="#" class="reportSpam_comment last" style="display: none;">举报</a>'+
						'</div>'+
						'{{#isOwner}}<a href="#" class="deleteComment">删除</a>{{/isOwner}}' +
						'<a href="#" class="{{like}}" like_count="{{praisedCount}}">{{praiseText}}</a><a href="#" '+
							'class="reply last">回复</a>'+
					'</div>'+
				'</div>'+
			'</li>{{/comments}}'+
		'</ul>'+
	'</div>';
	
	function authorHomeURLById(weiboUserId){
		return '/'+weiboUserId+'/profile';
	}
	
	$.ajax('http://www.davcai.com/list_cmnts?pid='+postId, {
		dataType: 'jsonp',
		cache: false,
		success: function(result) {
			commentsLoading.hide();
			var loginUserId = result.userId;
			var cmnts = {
				comments: result.data,
				commentCount: result.data.length, 
				like: function(){
					return this.praised?"unlike":"like";
				},
				authorHomeURL: function(){
					return authorHomeURLById(this.author.weiboUserId);
				},
				praiseText: function(){
					var text = this.praised ? '已赞' : '赞';
					if (this.praisedCount>0){
						text += '(' + this.praisedCount + ')';
					}
					return text;
				},
				isOwner: function(){
					return this.author.weiboUserId == loginUserId;
				},
				createTimeLong: function(){
					var date = $.strToDate(this.createTime);
					return date.getTime();
				},
				createTimeSpan: function(){
					return $.shortTime(this.createTime);
				},
				replyTo: function(){
					if (this.repliedComment){
						var homeURL = authorHomeURLById(this.repliedUser.weiboUserId);
						return ' 回复 '+ '<a href="'+homeURL+'">'+this.repliedUser.nickName+'</a>';
					}else{
						return '';
					}
				}
			};
			var tpl = $($.mustache(comments, cmnts));
			
			//转换评论中的@用户
			tpl.find(".cmt_con").each(function(index, elt) {
				$(elt).at();
			})
			//转换赛事链接
			tpl.match$ToHtml();
			//转换表情图标
			tpl.emotionsToHtml();
			
			tpl.find('li:last').addClass('last');
			addLikeHandler(tpl);
			addReplyHandler(tpl, post, postId);
			addDeleteHandler(tpl);
			addHoverHandler(tpl);
			// 插入或替换DOM树
			var commentList = post.find('.commentInfo');
			var hasComments = result.data.length;
			if (commentList.length > 0){
				commentList.replaceWith(tpl);
			}else if (hasComments>0){
				post.find('.commentContainer').append(tpl);
			}
		},
		error: function(){
			commentsLoading.hide();
			$.msgBox('抱歉，因网络问题，操作失败。', null, {pos:'center', success: false});
			//alert('抱歉，因网络问题，操作失败。');
		}
	});
}

function addHoverHandler(comments){
	comments.find('.commentList li').hover(
		function(){
			$(this).find('.deleteComment').css({'visibility': 'visible'});
		},
		function(){
			$(this).find('.deleteComment').css({'visibility': 'hidden'});
		}
	);
}

function addLikeHandler(comments){
	var like = comments.find('.like,.unlike');
	like.on('click', function(event){
		event.preventDefault();
		var self = $(this);
		var commentId = self.closest('li').attr('_comment');
		var praiseOrUnpraise = self.hasClass('like');
		praiseComment(commentId, praiseOrUnpraise, self);
	});
}

function addDeleteHandler(comments) {
	var deleteLink = comments.find('.deleteComment');
	deleteLink.on('click', function(event){
		event.preventDefault();
		var self = $(this);
		var commentId = self.closest('li').attr('_comment');
		deleteComment(commentId, self);
	});
}

function deleteComment(commentId, deleteLink) {
	var cmntLi = deleteLink.closest('li');
	var allCmnt = cmntLi.closest('.commentInfo');
	$.ajax('http://www.davcai.com/del_cmnt', {
		dataType: 'jsonp',
		data: {
			cid: commentId
		},
		success: function(result){
			if(result.success){
				$.msgBox('删除成功！', null, {pos:'center'});
				updateCmtCountInWeiboMeta(allCmnt, -1);
				var cmntCount = updateCommentCount(allCmnt);
				if (cmntCount == 0)
					allCmnt.remove();
				else
					cmntLi.remove();
			}else{
				$.msgBox(result.desc, null, {pos:'center', success: false});
			}
		},
		error: function(result){
			$.msgBox('因网络问题，删除失败。', null, {pos:'center', success: false});
		}
	});
}

// 更新“全部评论（2）”中的数字
// 返回：更新后的评论数
function updateCommentCount(allCmnt) {
	var countEl = allCmnt.find('.commentNum .counts');
	var cmntCountText = countEl.text();
	var pt = /（(\d+)）/;
	var groups = pt.exec(cmntCountText);
	var count = parseInt(groups[1]);
	count -= 1;
	countEl.text("（"+count+"）");
	return count;
}

function praiseComment(commentId, praiseOrUnpraise, praiseLink){
	$.ajax('http://www.davcai.com/prs_cmnt',{
		dataType: 'jsonp',
		data: {
			cid: commentId,
			add: praiseOrUnpraise
		},
		success: function(result){
			var count = parseInt(praiseLink.attr('like_count'));
			count = praiseOrUnpraise ? count+1 : Math.max(0,count-1);
			var likeText = '赞';
			if (praiseOrUnpraise){
				praiseLink.removeClass('like');
				praiseLink.addClass('unlike');
				likeText = '已赞';
			}else{
				praiseLink.removeClass('unlike');
				praiseLink.addClass('like');
				likeText = '赞';
			}
			praiseLink.attr('like_count', count);
			if (count>0){
				praiseLink.text(likeText+'('+count+')');
			}else {
				praiseLink.text(likeText);
			}
		},
		error: function(){
			$.msgBox('抱歉，因网络问题操作失败。', null, {pos:'center', success: false});
			//alert('抱歉，因网络问题操作失败。');
		}
	});
}

function addReplyHandler(commentInfo, post, postId){
	var replies = commentInfo.find('.reply');
	var commentsLoading = post.find('.loading');
	
	replies.on('click', function(event){
		event.preventDefault();
		var replyLink = $(this);
		var cid = replyLink.closest('li').attr('_comment');
		var comment = replyLink.closest('li');
		var commentPostArea = comment.find('.commentPostArea');
		// 显示“回复”文本框
		if (commentPostArea.length>0){
			commentPostArea.toggle();
		}else{
			comment.append(commentPostAreaTemplate);
		}
		var replyArea = comment.find("textarea");
		bindWeiboButtonEvent(replyArea);
		
		// 自动调整箭头位置
		adjustArrowPos(comment, replyLink);
		// 绑定回复按钮事件处理
		comment.find('form').submit(function(event){
			event.preventDefault();
			var textarea = comment.find('textarea');
			if (textarea.val()==''){
				return;
			}
			var commentText = textarea.val();
			if (!validateCommentText(commentText)){
				return;
			}
			commentsLoading.show();
			commentPostArea.hide();
			$.ajax('http://www.davcai.com/post_cmnt', {
				dataType: 'jsonp',
				method: 'post',
				data: {
					pid: postId,
					cid: cid,
					forward: comment.find('input:checkbox').is(':checked'),
					comment: textarea.val()
				},
				success: function(result){
					updateCmtCountInWeiboMeta(commentPostArea,1);
					loadComments(postId, commentsLoading, post);
				},
				error: function(){
					commentsLoading.hide();
					$.msgBox('抱歉，因网络问题操作失败。', null, {pos:'center', success: false});
					//alert('抱歉，因网络问题回复失败。');
				}
			});
			textarea.val('');
		});
	});
}


// ================= 我的评论页面中直接回复按钮响应方法 =====================

function onCommentMeReplyClick(link){
	var post = link.closest('li').find('.commentPostArea');
	if(!post.isEmpty()) {
		post.toggle();
	} else {
		var li = link.closest('li');
		li.append(commentPostAreaTemplate);
		bindCommentSubmit(li);
	}
}

function bindCommentSubmit(li){
	var form = li.find('form');
	var postId = li.attr('_post');
	var link = li.find('.ops .toggleComment');
	var cid = link.attr('_cid');
	var commentArea = li.find('.commentPostArea');
	var textarea = li.find('textarea');
	textarea.attr("id",cid+"textarea");
	//绑定插入表情事件
	$(".editor>.selector>.face[comment_face]").jqfaceedit({
    	txtAreaObj:textarea,
    	containerObj:$('#emotion_icons'),
    	textareaid:cid+"textarea",
    	top:30,
    	left:-40
    });
	
	//绑定$事件
	WB_API.addAtWhoEvent(textarea[0]);
	//绑定@事件
	WB_API.addNickNameAtWho(textarea[0]);
	//绑定点击赛事按钮事件
	WB_API.showMatchAreaDelegateEvents();
	
	form.submit(function(event){
		event.preventDefault();
		var textarea = li.find('textarea');
		if (textarea.val()==''){
			return;
		}
		$.ajax('http://www.davcai.com/post_cmnt', {
			dataType: 'jsonp',
			method: 'post',
			data: {
				pid: postId,
				cid: cid,
				forward: li.find('input:checkbox').is(':checked'),
				comment: textarea.val()
			},
			success: function(result){
				commentArea.hide();
				$.msgbox('回复成功。', link);
			},
			error: function(){
				$.msgbox('抱歉，因网络问题回复失败。', link, {success: false});
			}
		});
		textarea.val('');
	});
}


//========== 在微博的meta中更新微博数量 ============
function updateCmtCountInWeiboMeta(commentContainer,i){
	var $li = commentContainer.closest("li");
	var $cmt = $('[_n="comment"]',$li);
	var cmntCountText = $cmt.text();
	var pt = /\((\d+)\)/;
	var groups = pt.exec(cmntCountText);
	var count = 0;
	if(null != groups){
		count = parseInt(groups[1]);
		count += i;
		if(count > 0) {
			$cmt.text("评论("+count+")");
		} else {
			$cmt.text("评论");
		}
	} else {
		$cmt.text("评论("+1+")");
	}
	return count;
}


//========== 绑定@人，$赛事，加表情的事件 ============
var bindWeiboButtonEvent = function(commentTextArea) {
	//绑定$事件
	WB_API.addAtWhoEvent(commentTextArea[0]);
	//绑定@事件
	WB_API.addNickNameAtWho(commentTextArea[0]);
	//绑定点击赛事按钮事件
	WB_API.showMatchAreaDelegateEventsWith(commentTextArea.closest(".commentPostArea"));
	
	//为每一个评论框生成一个唯一ID
	var commentTextAreaId = commentTextArea.attr("id");
	if(!commentTextAreaId) {
		commentTextAreaId = new Date().getTime();
		commentTextArea.attr("id", commentTextAreaId);
	}
	//绑定插入表情事件
	$(".editor>.selector>.face[comment_face]").jqfaceedit({
    	txtAreaObj:$("#" + commentTextAreaId),
    	containerObj:$('#emotion_icons'),
    	textareaid:commentTextAreaId,
    	top:30,
    	left:-40
    });
};
/**
 * 异步加载首页公告和中奖喜报内容
 * */
$(function() {
	//异步显示"公告"列表
	ajaxMarrowData($("#announceList"), 1, "/ajax/ajaxLoadAnnounce", render_announce, null);
	
	//异步显示"中间喜报"列表
	//ajaxMarrowData($("#winningNewsList"), 1, "/ajax/ajaxLoadWinningNews", render_winning, null);
});

/**显示和隐藏加载进度*/
var showLoading = function(container) {
	container.next('.loading').show();
};
var hideLoading = function(container) {
	container.next('.loading').hide();
};

/**
 * page:页号
 * url:请求地址
 * render:渲染方法
 * ajaxPagerFn:分页按钮点击事件
 */
var ajaxMarrowData = function(container, page, url, render, ajaxPagerFn) {
	showLoading(container);
	var pager = {page:1};
	if(null != page && page > 0) {
		pager.page = page;
	}
	$.ajax({
		url: url,
		dataType: 'json',
		data: pager,
		cache: false,
		success: function(json){
			hideLoading(container);
			if(json != null && json.success == true
					&& json.data.results != null
					&& container.length > 0) {
				render.call(this, json, container);
			}
		}
	});
}

var lotteryName = {
	JCZQ : "JCZQ",
	JCLQ : "JCLQ",
	CTZC : "CTZC",
	SSQ : "SSQ",
	JX11 : "JX11",
	BJDC :"BJDC",
	getViewName : function(lottery) {
		var name = null;
		switch (lottery) {
		case this.JCZQ:
			name = "竞彩足球";
			break;
		case this.JCLQ:
			name = "竞彩蓝球";
			break;
		case this.CTZC:
			name = "传统足彩";
			break;
		case this.SSQ:
			name = "双色球";
			break;
		case this.JX11:
			name = "江西十一选五";
			break;
		case this.BJDC:
			name = "北京单场";
			break;
		}
		return name;
	}
};

//====================================精华列表渲染===================================================
//将渲染好的数据追加到DOM中，同时绑定系列事件
var appendDomAndEventBind = function(container, data) {
	container.html(data);
	container.emotionsToHtml();
	bindMouseInHeadOrNicknameEvent(container);
};
//渲染公告列表
function render_announce(json, container){
	var results = json.data.results;
	var segment = $.mustache(announceTemplate, {
		announces: results.splice(0,5)
	});
	appendDomAndEventBind(container, segment);
}

//渲染中奖喜报列表
function render_winning(json, container){
	var results = json.data.results;
	var segment = $.mustache(winningTemplate, {
		winnings: results.splice(0,5),
		lotteryName: function(){
			var lotteryId = this.lotteryId;
			return lotteryName.getViewName(lotteryId);
		},
		certificationTypeView:function (){
			if(this.weiboUser.certificationType == 1){
				return '<i class="niu" title="大V彩牛人" style="background:url(\'../images/niu.png\') 50% no-repeat;width: 32px;height: 15px;display: inline-block;"></i>';
			} else {
				return '';
			}
		}
	});
	appendDomAndEventBind(container, segment);
}

//====================================列表相关模板===================================================
//公告列表模板
var announceTemplate =
'<ul class="tipstopic-list">{{#announces}}'+
'	<li><span class="item">' +
'		<a href="/{{ownerId}}/{{id}}" target="_blank">{{&content}}</a>'+
'	</span></li>'+
'{{/announces}}</ul>';

//中奖喜报列表模板
var winningTemplate =
'{{#winnings}}' +
	'<li>' +
	    '<div class="headpic" _userid="{{userId}}">' +
	        '<a href="/{{userId}}/profile" target="_blank"><img width="50px" height="50px" src="{{weiboUser.headImageURL}}"></a>' +
	    '</div>' +
	    '<div class="content winning">' +
	        '<a href="/{{userId}}/profile" target="_blank"><span _nickname_block="_nickname_block" _userid="{{userId}}">{{weiboUser.nickName}}{{{certificationTypeView}}}</span></a>' +
	        '<div class="userDes">{{lotteryName}}</div>' +
	        '<div class="userDes"><a target="_blank" href="/{{userId}}/{{weiboId}}"><span class="winColor">中奖：{{bonus}}元</span></a></div>' +
	    '</div>' +
	'</li>' +
'{{/winnings}}';



//var interestUserTempelate = 
//	 '<li style="position: relative;" _flag="interest_user_li" _index="{4}" _li_index="{5}">'
//	+'	<div class="headpic"  _userid="{0}">'
//	+'		<a href="http://www.davcai.com/{0}/profile">'
//	+'			<img width="50px" height="50px" src="{1}">'
//	+'		</a>'
//	+'	</div>'
//	+'	<div class="content">'
//	+'		<div class="op addFriendBtn" title="加关注" _userid="{0}" flag="followFlag" style="margin-right: 2px;">+加关注</div>'
//	+'			<a href="http://www.davcai.com/{0}/profile" target="_blank" style="max-width: 130px;">'
//	+'				<span class="screen_name" _nickname_block="_nickname_block" _userid="{0}">{2}</span>{6}'
//	+'			</a>'
//	+'		<div class="userDes">我们共同关注{3}人</div>'
//	+'	</div>'
//	+'</li>';
//
//var INTEREST_USER_SIZE = 6; //感兴趣的人列表显示条数
//var interestUserContainer; //放用户的容器
//var loadingImgForInterest; //加载logo
//var interestUserData; //用户数据
//var interestUserStatus = new Array(); //标记用户状态的数组,正数（含0）表示正常显示，-1表示未显示，-2表示已删除（已经关注）
//$(document).ready(function() {
//	loadingImgForInterest = $("#interest_user_list_loading");
//	interestUserContainer = $("#interest_user_list");
//	
//	$.post("http://www.davcai.com/interest_user?size=60", function(data , e) {
//		loadingImgForInterest.hide();
//		dealResults(data);
//	}, 'json');
//	$("#change_interest_btn").click(changeInterestUser);
//});
//
//function changeInterestUser(event){
//	var changeBtn = $("#change_interest_btn");
//	var tmpIndexArr = new Array();
//	var tmpDataArr = new Array();
//	for ( var i = 0; i < interestUserData.length; i++) {
//		if(interestUserStatus[i] == -1){ //未显示的数据
//			tmpIndexArr.push(i);
//		} else if(interestUserStatus[i] >= 0){ 
//			interestUserStatus[i] = -1;//已显示的数据 设为未显示
//			tmpIndexArr.push(i);
//		}
//	}
//	//打乱顺序，产生随机效果
//	tmpIndexArr.sort(function(){ return 0.5 - Math.random(); }); 
//	
//	for ( var i = 0; i < INTEREST_USER_SIZE && i < tmpIndexArr.length; i++) {
//		var userIndex = tmpIndexArr[i];
//		if(interestUserStatus[userIndex] == -1){ //未显示
//			tmpDataArr[i] = interestUserData[userIndex];	
//			interestUserStatus[userIndex] = userIndex; //设置为显示状态
//		}
//	}
//	interestUserContainer.html('');
//	var segment = "";
//	for ( var i = 0; i < tmpDataArr.length && i < INTEREST_USER_SIZE; i++) {
//		var user = tmpDataArr[i];
//		segment += $.format(
//				interestUserTempelate,
//				user.weiboUserId,
//				user.headImageURL,
//				user.nickName,
//				user.togetherFollowNum,
//				i,
//				i,
//				user.certificationType == 1? '<i class="niu" title="大V彩牛人" style="background:url(\'../images/niu.png\') 50% no-repeat;width: 32px;height: 20px;display: inline-block;"></i>':"");
//	}
//	interestUserContainer.html(segment);
//	//给关注按钮绑定关注和取消关注事件
//	$(".addFriendBtn",interestUserContainer).click(function (event, a, b) {
//		followForInterest( $(event.target));
//	});
//	bindMouseInHeadOrNicknameEvent(interestUserContainer);
//}
//
//function dealResults(data){
//	if (data != null) {
//		if (data.success) {
//			if(data.results.length > 0){
//				interestUserData = data.results;
//				//初始化标记用户显示的数组
//				for ( var i = 0; i < interestUserData.length; i++) {
//					interestUserStatus[i] = -1;
//				}
//				var segment = "";
//				for ( var i = 0; i < interestUserData.length && i < INTEREST_USER_SIZE; i++) {
//					var user = interestUserData[i];
//					segment += $.format(
//							interestUserTempelate,
//							user.weiboUserId,
//							user.headImageURL,
//							user.nickName,
//							user.togetherFollowNum,
//							i,
//							i,
//							user.certificationType == 1? '<i class="niu" title="大V彩牛人" style="background:url(\'../images/niu.png\') 50% no-repeat;width: 32px;height: 20px;display: inline-block;"></i>':"");
//					interestUserStatus[i] = i;
//				}
//				interestUserContainer.html(segment);
//				//给关注按钮绑定关注和取消关注事件
//				$(".addFriendBtn",interestUserContainer).click(function (event, a, b) {
//					followForInterest( $(event.target));
//				});
//				bindMouseInHeadOrNicknameEvent(interestUserContainer);
//			} else {
//				interestUserContainer.html('<li style="position: relative;">'
//						+ '没有啦'
//						+ '</li>');
//			}
//		} else {
//			$.showMessage(data.desc);
//		}
//	}
//}
//function loadOneUser(liIndex){
//	var userLi = $("[_flag='interest_user_li']");
//	if(userLi.length < 1){
//		interestUserContainer.html('<li style="position: relative;">'
//				+ '没有啦'
//				+ '</li>');
//	}
//	for ( var i = 0; i < interestUserData.length; i++) {
//		if(interestUserStatus[i] == -1){
//			var user = interestUserData[i];
//			var segment = $.format(
//					interestUserTempelate,
//					user.weiboUserId,
//					user.headImageURL,
//					user.nickName,
//					user.togetherFollowNum,
//					i,
//					liIndex,
//					user.certificationType == 1? '<i class="niu" title="大V彩牛人" style="background:url(\'../images/niu.png\') 50% no-repeat;width: 32px;height: 20px;display: inline-block;"></i>':"");
//			interestUserStatus[i] = i;
//			if(userLi.length <= liIndex){
//				$(userLi[userLi.length-1]).after(segment);
//			} else {
//				$(userLi[liIndex]).before(segment);
//			}
//			var userLi = $("[_flag='interest_user_li']");
//			//给关注按钮绑定关注和取消关注事件
//			$(".addFriendBtn",$(userLi[liIndex])).click(function (event, a, b) {
//				followForInterest( $(event.target));
//			});
//			break;
//		}
//	}
//}
//function followForInterest(followBtn){
//	var followFlag = "followFlag";
//	if(followFlag == followBtn.attr("flag")) {
//		var userId = followBtn.attr("_userId");
//		$.post("http://www.davcai.com/ajax_rltship_follow",{followIds:userId}, function(data , e) {
//			if (data != null) {
//				if (data.success) {
//					var li = followBtn.parent().parent();
//					var index = li.attr("_index");
//					var liIndex = li.attr("_li_index"); 
//					interestUserData[index] = -1;
//					interestUserStatus[index] = -2;
//					li.fadeOut("slow",function (){li.remove();loadOneUser(liIndex);});
//				} else {
//					alert(data.desc);
//					if(data.resultCode == "notlogin"){
//						window.location.href="http://www.davcai.com/";
//					}
//				}
//			}
//		}, 'json');
//	}
//}
$(document).ready(function() {
	// 加载微博全部面板
	$("[_allposts]").find(".status-list").weiboListPanel({
		loadWeiboUrl : LT.Settings.URLS.weibo.loadPost,
		newWeiboUrl : LT.Settings.URLS.weibo.weibo_new_posts,
		newWeiboTimerUrl : LT.Settings.URLS.weibo.newPostTimer
	});	
	
	$('#allposts').click(function (){
		if($(this).hasClass("active")){
			return ;
		}
		$("[_weibotab]").removeClass("active");
		$(this).addClass("active");
		$("[_weibopanel]").hide();
		$("[_allposts]").show();
		$("[_allposts]").html('<ul class="status-list"></ul>');
		// 加载微博全部面板
		$("[_allposts]").find(".status-list").weiboListPanel({
			loadWeiboUrl : LT.Settings.URLS.weibo.loadPost,
			newWeiboUrl : LT.Settings.URLS.weibo.weibo_new_posts,
			newWeiboTimerUrl : LT.Settings.URLS.weibo.newPostTimer
		});	
	});
	$('#rec').click(function (){
		if($(this).hasClass("active")){
			return ;
		}
		$("[_weibotab]").removeClass("active");
		$(this).addClass("active");
		$("[_weibopanel]").hide();
		$("[_rec]").show();
		$("[_rec]").html('<ul class="status-list"></ul>');
		// 加载微博实单推荐面板
		$("[_rec]").find(".status-list").weiboListPanel({
			loadWeiboUrl : LT.Settings.URLS.weibo.loadRecommends,
			newWeiboUrl : LT.Settings.URLS.weibo.weibo_new_recommends,
			newWeiboTimerUrl : LT.Settings.URLS.weibo.newRecTimer
		});
	});
	$('#discuss').click(function (){
		if($(this).hasClass("active")){
			return ;
		}
		$("[_weibotab]").removeClass("active");
		$(this).addClass("active");
		$("[_weibopanel]").hide();
		$("[_discuss]").show();
		$("[_discuss]").html('<ul class="status-list"></ul>');
		// 加载微博讨论面板
		$("[_discuss]").find(".status-list").weiboListPanel({
			loadWeiboUrl : LT.Settings.URLS.weibo.loadDiscussWeibo,
			newWeiboUrl : LT.Settings.URLS.weibo.weibo_new_discuss,
			newWeiboTimerUrl : LT.Settings.URLS.weibo.newDiscussWeiboTimer
		});	
	});
	$('#news').click(function (){
		if($(this).hasClass("active")){
			return ;
		}
		$("[_weibotab]").removeClass("active");
		$(this).addClass("active");
		$("[_weibopanel]").hide();
		$("[_news]").show();
		$("[_news]").html('<ul class="status-list"></ul>');
		// 加载微博新闻面板
		$("[_news]").find(".status-list").weiboListPanel({
			loadWeiboUrl : LT.Settings.URLS.weibo.loadNews,
			newWeiboUrl : LT.Settings.URLS.weibo.weibo_new_news,
			newWeiboTimerUrl : LT.Settings.URLS.weibo.newNewsTimer
		});	
	});
});
/* 文件包含
 *
 *	 LTMath
 *   Date.prototype.format
 *   String.prototype.format
 *   medium.Util 
 *   medium.Share
 *   jQuery Templates Plugin 1.0.0pre
 *   jQuery Cookie Plugin
 *   jQuery nano
 *   jQuery mousewhell
 */

LTMath = new Object();
/**保留小数点函数*/
LTMath.roundNumber = function(num,v) {
	var f_x = parseFloat(num);
	if (isNaN(f_x)){
		return num;
	}
	f_x = Math.round(f_x*100)/100;
	var s_x = f_x.toString();
	var pos_decimal = s_x.indexOf('.');
	if (pos_decimal < 0){
		pos_decimal = s_x.length;
		s_x += '.';
	}
	while (s_x.length <= pos_decimal + v){
		s_x += '0';
	}
	return s_x;
};

/*检查倍数是否为正整数*/
;LTMath.isInteger = function (num){
	if(!num){
		return false;
	}
	if(!$.trim(num).match(/^[0-9]*[1-9][0-9]*$/)){
  		return false;
	}else{
		return true;
	}
};

/**
 * [format description]
 * 
 * @param {[type]}
 *            format [description]
 * @return {[type]} [description]
 */
;Date.prototype.Format = function(format){
	var o = {
		"M+" : this.getMonth()+1, // month
		"d+" : this.getDate(),    // day
		"h+" : this.getHours(),   // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth()+3)/3),  // quarter
		"S" : this.getMilliseconds() // millisecond
	};
	if(/(y+)/.test(format)){ 
		format=format.replace(RegExp.$1,(this.getFullYear()+"").substr(4 - RegExp.$1.length));
	}
	for(var k in o)if(new RegExp("("+ k +")").test(format)){
		format = format.replace(RegExp.$1,RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
	}
	return format;
};

;Date.prototype.cnformat = function(formatStr)   
{   
    var str = formatStr;   
    var Week = ['日','一','二','三','四','五','六'];  
  
    str=str.replace(/yyyy|YYYY/,this.getFullYear());   
    str=str.replace(/yy|YY/,(this.getYear() % 100)>9?(this.getYear() % 100).toString():'0' + (this.getYear() % 100));   
  
    str=str.replace(/MM/,this.getMonth()>9?(this.getMonth()+1).toString():'0' + (this.getMonth()+1));   
    str=str.replace(/M/g,this.getMonth()+1);   
  
    str=str.replace(/w|W/g,Week[this.getDay()]);   
  
    str=str.replace(/dd|DD/,this.getDate()>9?this.getDate().toString():'0' + this.getDate());   
    str=str.replace(/d|D/g,this.getDate());   
  
    str=str.replace(/hh|HH/,this.getHours()>9?this.getHours().toString():'0' + this.getHours());   
    str=str.replace(/h|H/g,this.getHours());   
    str=str.replace(/mm/,this.getMinutes()>9?this.getMinutes().toString():'0' + this.getMinutes());   
    str=str.replace(/m/g,this.getMinutes());   
  
    str=str.replace(/ss|SS/,this.getSeconds()>9?this.getSeconds().toString():'0' + this.getSeconds());   
    str=str.replace(/s|S/g,this.getSeconds());   
  
    return str;   
};
// var template1="我是{0}，今年{1}了";
// var result1=template1.format("loogn",22);
// var template2="我是{name}，今年{age}了";
// var result2=template2.format({name:"loogn",age:22});
String.prototype.format = function(args) {
    var result = this;
    if (arguments.length > 0) {    
        if (arguments.length == 1 && typeof (args) == "object") {
            for (var key in args) {
                if(args[key]!=undefined){
                    var reg = new RegExp("({" + key + "})", "g");
                    result = result.replace(reg, args[key]);
                }
            }
        }
        else {
            for (var i = 0; i < arguments.length; i++) {
                if (arguments[i] != undefined){
                    // var reg = new RegExp("({[" + i + "]})",
					// "g");//这个在索引大于9时会有问题
                	var reg = new RegExp("({)" + i + "(})", "g");
                    result = result.replace(reg, arguments[i]);
                }
            }
        }
    }
    return result;
};
;var medium = medium || {};
;(function($){
	medium.Util = {
		// parse a date in yyyy-mm-dd format(格式化后日期转换成日期)
		parseDate : function(input) {
		  var parts = input.match(/(\d+)/g);
		  // new Date(year, month [, date [, hours[, minutes[, seconds[,
			// ms]]]]])
		  return new Date(parts[0], parts[1]-1, parts[2]); // months are
															// 0-based
		},
	     /**
			 * 获取图片Url
			 */
	    getImageUrl : function(rid, w, h, cut) {
	      return TT_CONFIG.URL_CONSTANTS.RC_UPYUN_HTTP + rid;
	      var _w = w || 78,
	        _h = h || 78,
	        _qz = '', ds,
	        _other = arguments && arguments[4] ? arguments[4] : '',
	        rex = /\d/g, drs;
	      if (!rid) return;
	      cut = cut || 4;
	      ds = rid.match(rex);
	      _qz = (parseInt(ds[0]))%10;
	      drs = ['http://file-', _qz,TT_CONFIG.URL_CONSTANTS.GET_RCROOT_URL+'rc/getcuttedpicture?rid=', rid];
	      _h = _h == 'auto' ? '' : '&h=' + _h;
	      cut = cut == 'none' ? '' : '&cut=' + cut;
	      if (arguments[1] !== 'Y') {
	        drs = drs.concat(['&w=', _w, _h, cut, _other]);
	      }
	      return drs.join('');
	    },
	    
	 	/**
		 * 检查用户名是否合法
		 */
	    verifyNickName : function(name)
	 	{
	 		var reg = /([\^|~!@#$%&*()`=+:,.;?<>]|[\s]\[|\]|\\|\/|\'|\·|\~|\～|\"|\，|\。|\《|\》|\？|\；|\：|\’|\”|\！|\【|\】|\{|\｛|\｝|\}|\、|\＆|\……|\％|\￥|\＃|\＠|\×|\（|\）|\s)/ig;  // 非法字符
	 		return !reg.test(name);
	 	},
        getUsericon : function (icon){
            var strRegex = "^((https|http)?://)";
            var re=new RegExp(strRegex);
            return !!icon && !re.test(icon) ? this.getImageUrl(icon) : icon;
        },
        /**
		 * 过滤特殊字符
		 */
        replaceHtmlTag : function(value) {
            if(typeof value === "undefined" || !value) return "";
            return value.replace(/\</g, '&lt;').replace(/\>/g, '&gt;');
        },

        // 检查中英文字符长度
        strlen : function(str) {
            var len = 0;
            for (var i = 0; i < str.length; i++) {
                var c = str.charCodeAt(i);
                // 单字节加1
                if ((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) {
                    len++;
                }
                else {
                    len += 2;
                }
            }
            return len;
        },
        // 截取字符串，中文算2个字符
		cut_str : function(str, len){
        	var char_length = 0;
        	for (var i = 0; i < str.length; i++){
            	var son_str = str.charAt(i);
            	encodeURI(son_str).length > 2 ? char_length += 1 : char_length += 0.5;
            	if (char_length >= len){
                	var sub_len = char_length == len ? i+1 : i;
                	return str.substr(0, sub_len);
                	break;
            	}
        	}
    	},
		// 设置光标位置_el要设置的节点 _start位置（数字）
    	setCaret : function(_el,_start){
			var range = document.createRange();
        	var sel = window.getSelection();
        	try{
        		range.setStart(_el.get(0).childNodes[0], _start);
            	range.collapse(true);
            	sel.removeAllRanges();
            	sel.addRange(range);
			}
        	catch(e){
        		console.log(e);
        	}
    	},
	    validateEmail : function(text){
	    	  var bl = false;
	          var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;// 验证Mail的正则表达式,^[a-zA-Z0-9_-]:开头必须为字母,下划线,数字,
	          if (!!reg.test(text))
	          {
	              bl = true;
	          } 
	          return bl;
	    },
	    
        randomChar : function(l){
            var x="1234567890abcdefghijklmnopqrstuvwxyz";
            var tmp="";
            for(var i=0;i<l;i++) {
            tmp += x.charAt(Math.ceil(Math.random()*100000000)%x.length);
            }
            return tmp;
        },
        isURL : function (str_url){
            var re = new RegExp("((^http)|(^https)|(^ftp)|(^rtsp)|(^mms)):\/\/(\\w)+\.(\\w)+");
            if (re.test(str_url)){
                return (true); 
            }else{ 
                return (false); 
            }
        },
        generate_random_id : function(prefix) {
              var string;
              string = prefix + this.generate_random_char() + this.generate_random_char() + this.generate_random_char();
              while ($("#" + string).length > 0) {
                string += this.generate_random_char();
              }
              return string;
        },
        generate_random_char : function() {
              var chars, newchar, rand;
              chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXTZ";
              rand = Math.floor(Math.random() * chars.length);
              return newchar = chars.substring(rand, rand + 1);
        },
        /**
		 * [get_popover_placement description] popover 弹窗位置
		 * 
		 * @param {[type]}
		 *            tip [description]
		 * @param {[type]}
		 *            element [description]
		 * @return {[type]} [description]
		 */
        get_popover_placement: function(tip, element) {
            var $element, above, actualHeight, actualWidth, below, boundBottom, boundLeft, boundRight, boundTop, elementAbove, elementBelow, elementLeft, elementRight, isWithinBounds, left, pos, right;
            isWithinBounds = function(elementPosition) {
              return boundTop < elementPosition.top && boundLeft < elementPosition.left && boundRight > (elementPosition.left + actualWidth) && boundBottom > (elementPosition.top + actualHeight);
            };
            $element = $(element);
            pos = $.extend({}, $element.offset(), {
              width: element.offsetWidth,
              height: element.offsetHeight
            });
            console.log($element,pos);
            actualWidth = 330;
            actualHeight = 390;
            boundTop = $(document).scrollTop();
            boundLeft = $(document).scrollLeft();
            boundRight = boundLeft + $(document).width();
            boundBottom = boundTop + $(document).height();
            elementAbove = {
              top: pos.top - actualHeight,
              left: pos.left + pos.width / 2 - actualWidth / 2
            };
            elementBelow = {
              top: pos.top + pos.height,
              left: pos.left + pos.width / 2 - actualWidth / 2
            };
            elementLeft = {
              top: pos.top + pos.height / 2 - actualHeight / 2,
              left: pos.left - actualWidth
            };
            elementRight = {
              top: pos.top + pos.height / 2 - actualHeight / 2,
              left: pos.left + pos.width
            };
            elementBottomRight = {
              top: pos.top + pos.height,
              left: pos.left + pos.width / 2 - (actualWidth * 0.8)
            };
            elementBottomLeft = {
              top: pos.top + pos.height,
              left: pos.left + pos.width / 2 - (actualWidth * 0.2)
            };
            elementTopRight = {
                top: pos.top - actualHeight,
                left: pos.left + pos.width / 2 - (actualWidth * 0.8)
            };
            elementTopLeft = {
              top: pos.top - actualHeight,
              left: pos.left + pos.width / 2 - (actualWidth * 0.2)
            };
            above       = isWithinBounds(elementAbove);
            below       = isWithinBounds(elementBelow);
            left        = isWithinBounds(elementLeft);
            right       = isWithinBounds(elementRight);
            bottomRight = isWithinBounds(elementBottomRight);
            bottomLeft  = isWithinBounds(elementBottomLeft);
            topRight    = isWithinBounds(elementTopRight);
            topLeft     = isWithinBounds(elementTopLeft);

            if (above) {
                return "top";
            } else if (topRight) {
                return "top-right";
            } else if (topLeft) {
                return "top-left";
            }

            else if (below) {
                return "bottom";
            } else if (bottomRight) {
                return "bottom-right";
            } else if (bottomLeft) {
                return "bottom-left";
            }

            else if(left) {
                return "left";
            } else if(right) {
                return "right";
            } else {
                // default
                return "bottom";
            }
              
        } 
	};
    medium.Share = {
        remove_sharetag : function(str){
        str = str.replace(/<\/?[^>]*>/g,''); // 去除HTML tag
        str = str.replace(/　/g,'');// 去除中文空格
        str = str.replace(/(^\s*)|(\s*$)/g, ""); ;
        str = str.replace(/[ | ]*\n/g,'\n'); // 去除行尾空白
        // str = str.replace(/\n[\s| | ]*\r/g,'\n'); //去除多余空行
        str=str.replace(/&nbsp;/ig,'');// 去掉&nbsp;
        return str;
        },
        // share begin
        mb_strcut : function(str, maxlength, dot) {
            var len = 0;
            var res = '';
            str = this.remove_sharetag(str);
            var dot = !dot ? '...' : '';
            if (typeof page_charset == 'undefined') {
                page_charset = document.characterset;
            }
            maxlength = maxlength - dot.length;
            for ( var i = 0; i < str.length; i++) {
                len += str.charCodeAt(i) < 0 || str.charCodeAt(i) > 255 ? (page_charset == 'utf-8' ? 3 : 2) : 1;
                if (len > maxlength) {
                    res += dot;
                    break;
                }
                res += str.substr(i, 1);
            }
            return res;
        },
        qqweibo : function(evt){
            var node = $(evt.target);
            var opt = this.getShareParam(node);
            var _title = '我正在tuturead阅读《'+opt.title+'》。',_title = this.mb_strcut(_title+opt.summary,140),w=700,h=680;
            var _pic = !!opt.pic ? '&pic='+encodeURIComponent(opt.pic) :  '&pic='+encodeURIComponent(this.sitesharelogo);
            
            // var qqUrl = 'http://share.v.t.qq.com/index.php?c=share&a=index'
            // +'&title='+encodeURIComponent(_title)+'&url='+encodeURIComponent(opt.url)
            // +'&site='+encodeURIComponent('yasongju.tthuakan.com')
            // +_pic;

            // this.opurl(qqUrl,'qqweibo',w,h);
        },
        sitesharelogo : 'http://www.tuturead.com/assets/img/logo-icon_75.png',
        sinaweibo : function(opt){
            if (!!opt.summary){
                opt.summary = this.remove_sharetag(opt.summary);
            }
            var _title = '我在吐吐网发表了《'+opt.title+'》。',w=620,h=450;// _title =
																// this.mb_strcut(_title+opt.summary,240),
            var _pic = !!opt.pic ? '&pic='+encodeURIComponent(TT_CONFIG.URL_CONSTANTS.RC_UPYUN_HTTP+opt.pic) : '&pic='+encodeURIComponent(this.sitesharelogo);
            var sinaUrl = 'http://service.weibo.com/share/share.php?url='+encodeURIComponent(opt.url)
                +'&appkey='+TT_CONFIG.CONSTANTS.WEIBO_APPID
                +'&source=bshare'
                +'&retcode=0'
                +_pic
                +'&title='+encodeURIComponent(_title)+'(分享自:@吐吐网)'
                +'&appkey=&ralateUid=&dpc=1&state=1';
            // console.log(opt,111);
            this.opurl(sinaUrl,'sinaweibo',w,h);
            
        },
        douban : function(evt) {
            var node = $(evt.target);
            var opt = this.getShareParam(node);
            var _title = '我正在tuturead阅读《'+opt.title+'》。',_title = this.mb_strcut(_title,140),
            s1=window.getSelection,s2=document.getSelection,
            s3=document.selection,s=s1?s1():s2?s2():s3?s3.createRange().text:'',
            w=450,h=330;
            var _pic = !!opt.pic ? '&image='+encodeURIComponent(opt.pic) :  '&image='+encodeURIComponent(this.sitesharelogo);
            var _summary = this.mb_strcut(opt.summary,240);
            var doubanUrl = 'http://www.douban.com/recommend/?url='+encodeURIComponent(opt.url)
                +'&title='+encodeURIComponent(_title)
                +'&sel='+encodeURIComponent(_summary)
                +_pic
                +'&v=1';
                // +'&name='+encodeURIComponent(_summary)//encodeURIComponent(this.mb_strcut(opt.summary,140))
                // +'&text='+encodeURIComponent(_summary)
                

            this.opurl(doubanUrl,'douban',w,h);
        },
        opurl : function(r,name,w,h){
            var iOS = ( navigator.userAgent.match(/(iPad|iPhone|iPod)/g) ? true : false );
            var android = ( navigator.userAgent.match(/(android)/g) ? true : false );//
            if (iOS || android){
                var link = document.createElement('a');
                link.setAttribute("href", r);
                link.setAttribute("target", "_blank");

                var dispatch = document.createEvent("HTMLEvents");
                dispatch.initEvent("click", true, true);
                link.dispatchEvent(dispatch);
            }
            else{
                var x=function(){if(!window.open(r,name,'toolbar=0,resizable=1,scrollbars=yes,status=1,width='+w+',height='+h+',left='+(screen.width-w)/2+',top='+(screen.height-h)/2))location.href=r+'&r=1'};
                if(/Firefox/.test(navigator.userAgent)){setTimeout(x,0)}else{x()}
            }
        },
        
        weixin : function(node,_url,_css){
            node.weixinpanel({container:'#screen-content',url:_url,laycss:_css});//
            
          
        },
    };
})(window.jQuery);
;(function($){
    var weixinpanel = function(element,options){
    
        this.$el = $(element);
        this.options = $.extend({}, $.fn.weixinpanel.defaults, options);
        this.initialized();
    };
      
    weixinpanel.prototype = {
        initialized : function(){
            this.setup();
            this.settings = {};
            $.extend(this.settings, TT);
            this.settings.delegateEvents(this);// 继承events事件
        },
        events : {
            'click' : 'togglepop'
        },

        setup : function(){
            var options = this.options;
            this.popid =  (this.options.container) ? medium.Util.generate_random_id('popwx-') : 'popwx-';
            
            var html = $('#popweixin').tmpl({'id':this.popid,'url':encodeURIComponent(this.options.url),'laycss':this.options.laycss});
            $tip = $(html);
            this.placement = this.$el.attr('placement');

            if (typeof this.options.container == 'string'){
                this.options.container=$(this.options.container);
            }
            this.options.container ? $tip.appendTo(this.options.container) : $tip.appendTo(this.$el);
            // this.$el.html(html);
            

            this.$popwx = $('#'+this.popid);
            this.$masker = $('#masker');
            var zIndex = parseInt(this.$masker.filter(function() {
                return $(this).css('z-index') != 'auto';
            }).first().css('z-index'))+50;
            // this.show();
            this.$popwx.delegate('[data-type="weixinclose"]', 'click', $.proxy(function(event) {
                this.hide();
            },this));
            this.$popwx.css({'z-index':zIndex});
        },
        togglepop : function() {
            if (!this.$popwx.hasClass('fade')){
                // _hmt.push(['_trackEvent', 'share', 'click', 'weixin']);
                this.show();
            }
            else{
                this.hide();
            }
        },
        show : function(){
            this.$popwx.show().addClass('fade in');
            if (!!this.placement && this.placement=='center'){
                this.$popwx.css({
                    "top": "50%","left": "50%",
                    "margin-top": function () {
                        return - ($(this).outerHeight() / 2);
                    },
                    'margin-left': function () {
                        return -($(this).outerWidth() / 2);
                    }
                });
            }
            setTimeout($.proxy(function(){
                $('html').off('click.weixin.data-api').on('click.weixin.data-api', $.proxy(function (e) {
                  if (!!e && ($(e.target).closest('[data-type="pop-weixin"]').length>0 || $(e.target).closest('[data-action="share-on-weixin"]').length>0)){
                    e.stopPropagation() ;
                  }
                  else{
                    this.hide();
                  }
                },this));
            },this),20);
        },
        hide : function(){
            $('html').off('click.weixin.data-api');
            this.$popwx.hide().removeClass('fade in');
        }
    };
    
    $.fn.weixinpanel = function (option) {
       
      return this.each(function(input_field) {
            
        var $this = $(this)
            , data = $this.data('weixinpanel')
            , options = typeof option == 'object' && option;
            if (!data) {
                $this.data('weixinpanel', (data = new weixinpanel(this, options)))
            }
            else if(typeof option == 'object' && option){
                data['show']();
            }
            if (typeof option == 'string') data[option]();
          }); 
    };
    $.fn.weixinpanel.defaults = {url:'',content:'',heading:'',laycss:''};
    
})(window.jQuery);
/**
 * [description] confirmpanel
 * 
 * @author fugang
 * @param {[type]} $
 *            [description]
 * @return {[type]} [description]
 */
;(function($){
	var confirmpanel = function(element,options){
    
        this.$el = $(element);
        this.options = $.extend({}, $.fn.confirmpanel.defaults, options);
        this.initialized();
    };
      
    confirmpanel.prototype = {
        initialized : function(){
            this.setup();
            this.settings = {};
            $.extend(this.settings, TT);
            this.settings.delegateEvents(this);// 继承events事件
        },
        events : {
            'click [data-action="overlayconfirm"]' : 'overlayconfirm',
            'click [data-action="overlaycancel"]' : 'overlaycancel',
            'click [node-type="ajaxconfirm"]' : 'overlayclick'
        },
        setup : function(){
            var options = this.options;
            var html = $('#confirmbox').tmpl({'heading':options.heading,'body':options.body});
            this.$el.html(html);
            this.$overlay = this.$el.find('[data-action-scope="overlay"]');
            this.$cfgDlg = this.$el.find('.overlay-dialog');
            this.show();
        },
        show : function() {
            this.$overlay.show();
            this.$cfgDlg.show().modal('show').css({
                "top":"100px",
                // "margin-top": function () {
                // return - (174);//$(this).outerHeight() / 2
                // },
                'margin-left': function () {
                    return -($(this).outerWidth() / 2);
                }
            });
        },
        hide : function(){
            this.$cfgDlg.modal('hide');
        },
        overlayclick : function(evt){
            if ($(evt.target).closest('.overlay-dialog').length>0){
                evt.stopPropagation();
                return;
            }
            this.$el.trigger('confirm.cancel');
            this.overlayclose();
            // this.$cfgDlg.modal('hide');
        },
        overlayclose : function(evt){
            this.hide();
            this.$overlay.hide();
            // $(evt.target).closest('[data-action-scope="overlay"]').remove();
        },
        overlayconfirm : function(evt){
            this.overlayclose(evt);
            this.$el.trigger('confirm.ok');
            evt.stopPropagation();
            return;
        },
        
        overlaycancel : function(evt){
            this.overlayclose(evt);
            this.$el.trigger('confirm.cancel');
            evt.stopPropagation();
            return;
        },
    };
    
    $.fn.confirmpanel = function (option) {
       
      return this.each(function(input_field) {
            
        var $this = $(this)
            , data = $this.data('confirmpanel')
            , options = typeof option == 'object' && option;
            if (!data) {
                $this.data('confirmpanel', (data = new confirmpanel(this, options)))
            }
            else if(typeof option == 'object' && option){
                data['show']();
            }
            if (typeof option == 'string') data[option]();
          }); 
    };
    $.fn.confirmpanel.defaults = {
        heading: '',
        body:'Body contents',
        callback : null
    };
	// $.fn.extend({
 // //pass the options variable to the function
 // confirmModal: function (options) {
 // //
 // var context = $(this);
 // $('#ajaxConfirm',this).click(function(evt){
 // if ($(evt.target).closest('.overlay-dialog').length>0){
 // evt.stopPropagation();
 // return;
 // }
 // $cfgDlg.modal('hide');
 // });
            
 // $('#confirmCancelBtn',this).click(function(evt){
 // $cfgDlg.modal('hide');
 // evt.stopPropagation();
 // return;
 // });
            
 // $('#confirmYesBtn',this).click(function(evt){
 // if(options.callback!=null)
 // options.callback();
 // $cfgDlg.modal('hide');
 // evt.stopPropagation();
 // return;
 // });
 // }
 // });
})(window.jQuery);
;(function(e) {
    var t, o, n = {
        className: "autosizejs",
        append: "",
        callback: !1
    },
    i = "hidden",
    s = "border-box",
    a = "lineHeight",
    l = '<textarea tabindex="-1" style="position:absolute; top:-999px; left:0; right:auto; bottom:auto; border:0; -moz-box-sizing:content-box; -webkit-box-sizing:content-box; box-sizing:content-box; word-wrap:break-word; height:0 !important; min-height:0 !important; overflow:hidden;"/>',
    r = ["fontFamily", "fontSize", "fontWeight", "fontStyle", "letterSpacing", "textTransform", "wordSpacing", "textIndent"],
    c = "oninput",
    h = "onpropertychange",
    p = e(l).data("autosize", !0)[0];
    p.style.lineHeight = "99px",
    "99px" === e(p).css(a) && r.push(a),
    p.style.lineHeight = "",
    e.fn.autosize = function(a) {
        return a = e.extend({},
        n, a || {}),
        p.parentNode !== document.body && (e(document.body).append(p), p.value = "\n\n\n", p.scrollTop = 9e4, t = p.scrollHeight === p.scrollTop + p.clientHeight),
        this.each(function() {
            function n() {
                o = b,
                p.className = a.className,
                e.each(r,
                function(e, t) {
                    p.style[t] = f.css(t)
                })
            }
            function l() {
                var e, s, l;
                if (o !== b && n(), !d) {
                    d = !0,
                    p.value = b.value + a.append,
                    p.style.overflowY = b.style.overflowY,
                    l = parseInt(b.style.height, 10),
                    p.style.width = Math.max(f.width(), 0) + "px",
                    t ? e = p.scrollHeight: (p.scrollTop = 0, p.scrollTop = 9e4, e = p.scrollTop);
                    var r = parseInt(f.css("maxHeight"), 10);
                    r = r && r > 0 ? r: 9e4,
                    e > r ? (e = r, s = "scroll") : u > e && (e = u),
                    e += x,
                    b.style.overflowY = s || i,
                    l !== e && (b.style.height = e + "px", w && a.callback.call(b)),
                    setTimeout(function() {
                        d = !1
                    },
                    1)
                }
            }
            var u, d, g, b = this,
            f = e(b),
            x = 0,
            w = e.isFunction(a.callback);
            f.data("autosize") || ((f.css("box-sizing") === s || f.css("-moz-box-sizing") === s || f.css("-webkit-box-sizing") === s) && (x = f.outerHeight() - f.height()), u = Math.max(parseInt(f.css("minHeight"), 10) - x, f.height()), g = "none" === f.css("resize") || "vertical" === f.css("resize") ? "none": "horizontal", f.css({
                overflow: i,
                overflowY: i,
                wordWrap: "break-word",
                resize: g
            }).data("autosize", !0), h in b ? c in b ? b[c] = b.onkeyup = l: b[h] = l: b[c] = l, e(window).on("resize",
            function() {
                d = !1,
                l()
            }), f.on("autosize",
            function() {
                d = !1,
                l()
            }), l())
        })
    }
})(window.jQuery || window.Zepto);
/*
 * ! jQuery Cookie Plugin v1.4.0 https://github.com/carhartl/jquery-cookie
 * 
 * Copyright 2013 Klaus Hartl Released under the MIT license
 */
;(function (factory) {
    if (typeof define === 'function' && define.amd) {
        // AMD. Register as anonymous module.
        define(['jquery'], factory);
    } else {
        // Browser globals.
        factory(jQuery);
    }
}(function ($) {

    var pluses = /\+/g;

    function encode(s) {
        return config.raw ? s : encodeURIComponent(s);
    }

    function decode(s) {
        return config.raw ? s : decodeURIComponent(s);
    }

    function stringifyCookieValue(value) {
        return encode(config.json ? JSON.stringify(value) : String(value));
    }

    function parseCookieValue(s) {
        if (s.indexOf('"') === 0) {
            // This is a quoted cookie as according to RFC2068, unescape...
            s = s.slice(1, -1).replace(/\\"/g, '"').replace(/\\\\/g, '\\');
        }

        try {
            // Replace server-side written pluses with spaces.
            // If we can't decode the cookie, ignore it, it's unusable.
            // If we can't parse the cookie, ignore it, it's unusable.
            s = decodeURIComponent(s.replace(pluses, ' '));
            return config.json ? JSON.parse(s) : s;
        } catch(e) {}
    }

    function read(s, converter) {
        var value = config.raw ? s : parseCookieValue(s);
        return $.isFunction(converter) ? converter(value) : value;
    }

    var config = $.cookie = function (key, value, options) {

        // Write

        if (value !== undefined && !$.isFunction(value)) {
            options = $.extend({}, config.defaults, options);

            if (typeof options.expires === 'number') {
                var days = options.expires, t = options.expires = new Date();
                t.setTime(+t + days * 864e+5);
            }

            return (document.cookie = [
                encode(key), '=', stringifyCookieValue(value),
                options.expires ? '; expires=' + options.expires.toUTCString() : '', // use
																						// expires
																						// attribute,
																						// max-age
																						// is
																						// not
																						// supported
																						// by
																						// IE
                options.path    ? '; path=' + options.path : '',
                options.domain  ? '; domain=' + options.domain : '',
                options.secure  ? '; secure' : ''
            ].join(''));
        }

        // Read

        var result = key ? undefined : {};

        // To prevent the for loop in the first place assign an empty array
        // in case there are no cookies at all. Also prevents odd result when
        // calling $.cookie().
        var cookies = document.cookie ? document.cookie.split('; ') : [];

        for (var i = 0, l = cookies.length; i < l; i++) {
            var parts = cookies[i].split('=');
            var name = decode(parts.shift());
            var cookie = parts.join('=');

            if (key && key === name) {
                // If second argument (value) is a function it's a converter...
                result = read(cookie, value);
                break;
            }

            // Prevent storing a cookie that we couldn't decode.
            if (!key && (cookie = read(cookie)) !== undefined) {
                result[name] = cookie;
            }
        }

        return result;
    };

    config.defaults = {};

    $.removeCookie = function (key, options) {
        if ($.cookie(key) === undefined) {
            return false;
        }

        // Must not alter options, thus extending a fresh object...
        $.cookie(key, '', $.extend({}, options, { expires: -1 }));
        return !$.cookie(key);
    };

}));
/*
 * ! jQuery Templates Plugin 1.0.0pre http://github.com/jquery/jquery-tmpl
 * Requires jQuery 1.4.2
 * 
 * Copyright 2011, Software Freedom Conservancy, Inc. Dual licensed under the
 * MIT or GPL Version 2 licenses. http://jquery.org/license
 */
;(function( jQuery, undefined ){
    var oldManip = jQuery.fn.domManip, tmplItmAtt = "_tmplitem", htmlExpr = /^[^<]*(<[\w\W]+>)[^>]*$|\{\{\! /,
        newTmplItems = {}, wrappedItems = {}, appendToTmplItems, topTmplItem = { key: 0, data: {} }, itemKey = 0, cloneIndex = 0, stack = [];

    function newTmplItem( options, parentItem, fn, data ) {
        // Returns a template item data structure for a new rendered instance of
		// a template (a 'template item').
        // The content field is a hierarchical array of strings and nested items
		// (to be
        // removed and replaced by nodes field of dom elements, once inserted in
		// DOM).
        var newItem = {
            data: data || (data === 0 || data === false) ? data : (parentItem ? parentItem.data : {}),
            _wrap: parentItem ? parentItem._wrap : null,
            tmpl: null,
            parent: parentItem || null,
            nodes: [],
            calls: tiCalls,
            nest: tiNest,
            wrap: tiWrap,
            html: tiHtml,
            update: tiUpdate
        };
        if ( options ) {
            jQuery.extend( newItem, options, { nodes: [], parent: parentItem });
        }
        if ( fn ) {
            // Build the hierarchical content to be used during insertion into
			// DOM
            newItem.tmpl = fn;
            newItem._ctnt = newItem._ctnt || newItem.tmpl( jQuery, newItem );
            newItem.key = ++itemKey;
            // Keep track of new template item, until it is stored as jQuery
			// Data on DOM element
            (stack.length ? wrappedItems : newTmplItems)[itemKey] = newItem;
        }
        return newItem;
    }

    // Override appendTo etc., in order to provide support for targeting
	// multiple elements. (This code would disappear if integrated in jquery
	// core).
    jQuery.each({
        appendTo: "append",
        prependTo: "prepend",
        insertBefore: "before",
        insertAfter: "after",
        replaceAll: "replaceWith"
    }, function( name, original ) {
        jQuery.fn[ name ] = function( selector ) {
            var ret = [], insert = jQuery( selector ), elems, i, l, tmplItems,
                parent = this.length === 1 && this[0].parentNode;

            appendToTmplItems = newTmplItems || {};
            if ( parent && parent.nodeType === 11 && parent.childNodes.length === 1 && insert.length === 1 ) {
                insert[ original ]( this[0] );
                ret = this;
            } else {
                for ( i = 0, l = insert.length; i < l; i++ ) {
                    cloneIndex = i;
                    elems = (i > 0 ? this.clone(true) : this).get();
                    jQuery( insert[i] )[ original ]( elems );
                    ret = ret.concat( elems );
                }
                cloneIndex = 0;
                ret = this.pushStack( ret, name, insert.selector );
            }
            tmplItems = appendToTmplItems;
            appendToTmplItems = null;
            jQuery.tmpl.complete( tmplItems );
            return ret;
        };
    });

    jQuery.fn.extend({
        // Use first wrapped element as template markup.
        // Return wrapped set of template items, obtained by rendering template
		// against data.
        tmpl: function( data, options, parentItem ) {
            return jQuery.tmpl( this[0], data, options, parentItem );
        },

        // Find which rendered template item the first wrapped DOM element
		// belongs to
        tmplItem: function() {
            return jQuery.tmplItem( this[0] );
        },

        // Consider the first wrapped element as a template declaration, and get
		// the compiled template or store it as a named template.
        template: function( name ) {
            return jQuery.template( name, this[0] );
        },

        domManip: function( args, table, callback, options ) {
            if ( args[0] && jQuery.isArray( args[0] )) {
                var dmArgs = jQuery.makeArray( arguments ), elems = args[0], elemsLength = elems.length, i = 0, tmplItem;
                while ( i < elemsLength && !(tmplItem = jQuery.data( elems[i++], "tmplItem" ))) {}
                if ( tmplItem && cloneIndex ) {
                    dmArgs[2] = function( fragClone ) {
                        // Handler called by oldManip when rendered template has
						// been inserted into DOM.
                        jQuery.tmpl.afterManip( this, fragClone, callback );
                    };
                }
                oldManip.apply( this, dmArgs );
            } else {
                oldManip.apply( this, arguments );
            }
            cloneIndex = 0;
            if ( !appendToTmplItems ) {
                jQuery.tmpl.complete( newTmplItems );
            }
            return this;
        }
    });

    jQuery.extend({
        // Return wrapped set of template items, obtained by rendering template
		// against data.
        tmpl: function( tmpl, data, options, parentItem ) {
            var ret, topLevel = !parentItem;
            if ( topLevel ) {
                // This is a top-level tmpl call (not from a nested template
				// using {{tmpl}})
                parentItem = topTmplItem;
                tmpl = jQuery.template[tmpl] || jQuery.template( null, tmpl );
                wrappedItems = {}; // Any wrapped items will be rebuilt, since
									// this is top level
            } else if ( !tmpl ) {
                // The template item is already associated with DOM - this is a
				// refresh.
                // Re-evaluate rendered template for the parentItem
                tmpl = parentItem.tmpl;
                newTmplItems[parentItem.key] = parentItem;
                parentItem.nodes = [];
                if ( parentItem.wrapped ) {
                    updateWrapped( parentItem, parentItem.wrapped );
                }
                // Rebuild, without creating a new template item
                return jQuery( build( parentItem, null, parentItem.tmpl( jQuery, parentItem ) ));
            }
            if ( !tmpl ) {
                return []; // Could throw...
            }
            if ( typeof data === "function" ) {
                data = data.call( parentItem || {} );
            }
            if ( options && options.wrapped ) {
                updateWrapped( options, options.wrapped );
            }
            ret = jQuery.isArray( data ) ?
                jQuery.map( data, function( dataItem ) {
                    return dataItem ? newTmplItem( options, parentItem, tmpl, dataItem ) : null;
                }) :
                [ newTmplItem( options, parentItem, tmpl, data ) ];
            return topLevel ? jQuery( build( parentItem, null, ret ) ) : ret;
        },

        // Return rendered template item for an element.
        tmplItem: function( elem ) {
            var tmplItem;
            if ( elem instanceof jQuery ) {
                elem = elem[0];
            }
            while ( elem && elem.nodeType === 1 && !(tmplItem = jQuery.data( elem, "tmplItem" )) && (elem = elem.parentNode) ) {}
            return tmplItem || topTmplItem;
        },

        // Set:
        // Use $.template( name, tmpl ) to cache a named template,
        // where tmpl is a template string, a script element or a jQuery
		// instance wrapping a script element, etc.
        // Use $( "selector" ).template( name ) to provide access by name to a
		// script block template declaration.

        // Get:
        // Use $.template( name ) to access a cached template.
        // Also $( selectorToScriptBlock ).template(), or $.template( null,
		// templateString )
        // will return the compiled template, without adding a name reference.
        // If templateString includes at least one HTML tag, $.template(
		// templateString ) is equivalent
        // to $.template( null, templateString )
        template: function( name, tmpl ) {
            if (tmpl) {
                // Compile template and associate with name
                if ( typeof tmpl === "string" ) {
                    // This is an HTML string being passed directly in.
                    tmpl = buildTmplFn( tmpl );
                } else if ( tmpl instanceof jQuery ) {
                    tmpl = tmpl[0] || {};
                }
                if ( tmpl.nodeType ) {
                    // If this is a template block, use cached copy, or generate
					// tmpl function and cache.
                    tmpl = jQuery.data( tmpl, "tmpl" ) || jQuery.data( tmpl, "tmpl", buildTmplFn( tmpl.innerHTML ));
                    // Issue: In IE, if the container element is not a script
					// block, the innerHTML will remove quotes from attribute
					// values whenever the value does not include white space.
                    // This means that foo="${x}" will not work if the value of
					// x includes white space: foo="${x}" -> foo=value of x.
                    // To correct this, include space in tag: foo="${ x }" ->
					// foo="value of x"
                }
                return typeof name === "string" ? (jQuery.template[name] = tmpl) : tmpl;
            }
            // Return named compiled template
            return name ? (typeof name !== "string" ? jQuery.template( null, name ):
                (jQuery.template[name] ||
                    // If not in map, and not containing at least on HTML tag,
					// treat as a selector.
                    // (If integrated with core, use quickExpr.exec)
                    jQuery.template( null, htmlExpr.test( name ) ? name : jQuery( name )))) : null;
        },

        encode: function( text ) {
            // Do HTML encoding replacing < > & and ' and " by corresponding
			// entities.
            return ("" + text).split("<").join("&lt;").split(">").join("&gt;").split('"').join("&#34;").split("'").join("&#39;");
        }
    });

    jQuery.extend( jQuery.tmpl, {
        tag: {
            "tmpl": {
                _default: { $2: "null" },
                open: "if($notnull_1){__=__.concat($item.nest($1,$2));}"
                // tmpl target parameter can be of type function, so use $1, not
				// $1a (so not auto detection of functions)
                // This means that {{tmpl foo}} treats foo as a template (which
				// IS a function).
                // Explicit parens can be used if foo is a function that returns
				// a template: {{tmpl foo()}}.
            },
            "wrap": {
                _default: { $2: "null" },
                open: "$item.calls(__,$1,$2);__=[];",
                close: "call=$item.calls();__=call._.concat($item.wrap(call,__));"
            },
            "each": {
                _default: { $2: "$index, $value" },
                open: "if($notnull_1){$.each($1a,function($2){with(this){",
                close: "}});}"
            },
            "if": {
                open: "if(($notnull_1) && $1a){",
                close: "}"
            },
            "else": {
                _default: { $1: "true" },
                open: "}else if(($notnull_1) && $1a){"
            },
            "html": {
                // Unecoded expression evaluation.
                open: "if($notnull_1){__.push($1a);}"
            },
            "=": {
                // Encoded expression evaluation. Abbreviated form is ${}.
                _default: { $1: "$data" },
                open: "if($notnull_1){__.push($.encode($1a));}"
            },
            "!": {
                // Comment tag. Skipped by parser
                open: ""
            }
        },

        // This stub can be overridden, e.g. in jquery.tmplPlus for providing
		// rendered events
        complete: function( items ) {
            newTmplItems = {};
        },

        // Call this from code which overrides domManip, or equivalent
        // Manage cloning/storing template items etc.
        afterManip: function afterManip( elem, fragClone, callback ) {
            // Provides cloned fragment ready for fixup prior to and after
			// insertion into DOM
            var content = fragClone.nodeType === 11 ?
                jQuery.makeArray(fragClone.childNodes) :
                fragClone.nodeType === 1 ? [fragClone] : [];

            // Return fragment to original caller (e.g. append) for DOM
			// insertion
            callback.call( elem, fragClone );

            // Fragment has been inserted:- Add inserted nodes to tmplItem data
			// structure. Replace inserted element annotations by jQuery.data.
            storeTmplItems( content );
            cloneIndex++;
        }
    });

    // ========================== Private helper functions, used by code above
	// ==========================

    function build( tmplItem, nested, content ) {
        // Convert hierarchical content into flat string array
        // and finally return array of fragments ready for DOM insertion
        var frag, ret = content ? jQuery.map( content, function( item ) {
            return (typeof item === "string") ?
                // Insert template item annotations, to be converted to
				// jQuery.data( "tmplItem" ) when elems are inserted into DOM.
                (tmplItem.key ? item.replace( /(<\w+)(?=[\s>])(?![^>]*_tmplitem)([^>]*)/g, "$1 " + tmplItmAtt + "=\"" + tmplItem.key + "\" $2" ) : item) :
                // This is a child template item. Build nested template.
                build( item, tmplItem, item._ctnt );
        }) :
        // If content is not defined, insert tmplItem directly. Not a template
		// item. May be a string, or a string array, e.g. from {{html
		// $item.html()}}.
        tmplItem;
        if ( nested ) {
            return ret;
        }

        // top-level template
        ret = ret.join("");

        // Support templates which have initial or final text nodes, or consist
		// only of text
        // Also support HTML entities within the HTML markup.
        ret.replace( /^\s*([^<\s][^<]*)?(<[\w\W]+>)([^>]*[^>\s])?\s*$/, function( all, before, middle, after) {
            frag = jQuery( middle ).get();

            storeTmplItems( frag );
            if ( before ) {
                frag = unencode( before ).concat(frag);
            }
            if ( after ) {
                frag = frag.concat(unencode( after ));
            }
        });
        return frag ? frag : unencode( ret );
    }

    function unencode( text ) {
        // Use createElement, since createTextNode will not render HTML entities
		// correctly
        var el = document.createElement( "div" );
        el.innerHTML = text;
        return jQuery.makeArray(el.childNodes);
    }

    // Generate a reusable function that will serve to render a template against
	// data
    function buildTmplFn( markup ) {
        return new Function("jQuery","$item",
            // Use the variable __ to hold a string array while building the
			// compiled template. (See
			// https://github.com/jquery/jquery-tmpl/issues#issue/10).
            "var $=jQuery,call,__=[],$data=$item.data;" +

            // Introduce the data as local variables using with(){}
            "with($data){__.push('" +

            // Convert the template into pure JavaScript
            jQuery.trim(markup)
                .replace( /([\\'])/g, "\\$1" )
                .replace( /[\r\t\n]/g, " " )
                .replace( /\$\{([^\}]*)\}/g, "{{= $1}}" )
                .replace( /\{\{(\/?)(\w+|.)(?:\(((?:[^\}]|\}(?!\}))*?)?\))?(?:\s+(.*?)?)?(\(((?:[^\}]|\}(?!\}))*?)\))?\s*\}\}/g,
                function( all, slash, type, fnargs, target, parens, args ) {
                    var tag = jQuery.tmpl.tag[ type ], def, expr, exprAutoFnDetect;
                    if ( !tag ) {
                        throw "Unknown template tag: " + type;
                    }
                    def = tag._default || [];
                    if ( parens && !/\w$/.test(target)) {
                        target += parens;
                        parens = "";
                    }
                    if ( target ) {
                        target = unescape( target );
                        args = args ? ("," + unescape( args ) + ")") : (parens ? ")" : "");
                        // Support for target being things like a.toLowerCase();
                        // In that case don't call with template item as 'this'
						// pointer. Just evaluate...
                        expr = parens ? (target.indexOf(".") > -1 ? target + unescape( parens ) : ("(" + target + ").call($item" + args)) : target;
                        exprAutoFnDetect = parens ? expr : "(typeof(" + target + ")==='function'?(" + target + ").call($item):(" + target + "))";
                    } else {
                        exprAutoFnDetect = expr = def.$1 || "null";
                    }
                    fnargs = unescape( fnargs );
                    return "');" +
                        tag[ slash ? "close" : "open" ]
                            .split( "$notnull_1" ).join( target ? "typeof(" + target + ")!=='undefined' && (" + target + ")!=null" : "true" )
                            .split( "$1a" ).join( exprAutoFnDetect )
                            .split( "$1" ).join( expr )
                            .split( "$2" ).join( fnargs || def.$2 || "" ) +
                        "__.push('";
                }) +
            "');}return __;"
        );
    }
    function updateWrapped( options, wrapped ) {
        // Build the wrapped content.
        options._wrap = build( options, true,
            // Suport imperative scenario in which options.wrapped can be set to
			// a selector or an HTML string.
            jQuery.isArray( wrapped ) ? wrapped : [htmlExpr.test( wrapped ) ? wrapped : jQuery( wrapped ).html()]
        ).join("");
    }

    function unescape( args ) {
        return args ? args.replace( /\\'/g, "'").replace(/\\\\/g, "\\" ) : null;
    }
    function outerHtml( elem ) {
        var div = document.createElement("div");
        div.appendChild( elem.cloneNode(true) );
        return div.innerHTML;
    }

    // Store template items in jQuery.data(), ensuring a unique tmplItem data
	// data structure for each rendered template instance.
    function storeTmplItems( content ) {
        var keySuffix = "_" + cloneIndex, elem, elems, newClonedItems = {}, i, l, m;
        for ( i = 0, l = content.length; i < l; i++ ) {
            if ( (elem = content[i]).nodeType !== 1 ) {
                continue;
            }
            elems = elem.getElementsByTagName("*");
            for ( m = elems.length - 1; m >= 0; m-- ) {
                processItemKey( elems[m] );
            }
            processItemKey( elem );
        }
        function processItemKey( el ) {
            var pntKey, pntNode = el, pntItem, tmplItem, key;
            // Ensure that each rendered template inserted into the DOM has its
			// own template item,
            if ( (key = el.getAttribute( tmplItmAtt ))) {
                while ( pntNode.parentNode && (pntNode = pntNode.parentNode).nodeType === 1 && !(pntKey = pntNode.getAttribute( tmplItmAtt ))) { }
                if ( pntKey !== key ) {
                    // The next ancestor with a _tmplitem expando is on a
					// different key than this one.
                    // So this is a top-level element within this template item
                    // Set pntNode to the key of the parentNode, or to 0 if
					// pntNode.parentNode is null, or pntNode is a fragment.
                    pntNode = pntNode.parentNode ? (pntNode.nodeType === 11 ? 0 : (pntNode.getAttribute( tmplItmAtt ) || 0)) : 0;
                    if ( !(tmplItem = newTmplItems[key]) ) {
                        // The item is for wrapped content, and was copied from
						// the temporary parent wrappedItem.
                        tmplItem = wrappedItems[key];
                        tmplItem = newTmplItem( tmplItem, newTmplItems[pntNode]||wrappedItems[pntNode] );
                        tmplItem.key = ++itemKey;
                        newTmplItems[itemKey] = tmplItem;
                    }
                    if ( cloneIndex ) {
                        cloneTmplItem( key );
                    }
                }
                el.removeAttribute( tmplItmAtt );
            } else if ( cloneIndex && (tmplItem = jQuery.data( el, "tmplItem" )) ) {
                // This was a rendered element, cloned during append or appendTo
				// etc.
                // TmplItem stored in jQuery data has already been cloned in
				// cloneCopyEvent. We must replace it with a fresh cloned
				// tmplItem.
                cloneTmplItem( tmplItem.key );
                newTmplItems[tmplItem.key] = tmplItem;
                pntNode = jQuery.data( el.parentNode, "tmplItem" );
                pntNode = pntNode ? pntNode.key : 0;
            }
            if ( tmplItem ) {
                pntItem = tmplItem;
                // Find the template item of the parent element.
                // (Using !=, not !==, since pntItem.key is number, and pntNode
				// may be a string)
                while ( pntItem && pntItem.key != pntNode ) {
                    // Add this element as a top-level node for this rendered
					// template item, as well as for any
                    // ancestor items between this item and the item of its
					// parent element
                    pntItem.nodes.push( el );
                    pntItem = pntItem.parent;
                }
                // Delete content built during rendering - reduce API surface
				// area and memory use, and avoid exposing of stale data after
				// rendering...
                delete tmplItem._ctnt;
                delete tmplItem._wrap;
                // Store template item as jQuery data on the element
                jQuery.data( el, "tmplItem", tmplItem );
            }
            function cloneTmplItem( key ) {
                key = key + keySuffix;
                tmplItem = newClonedItems[key] =
                    (newClonedItems[key] || newTmplItem( tmplItem, newTmplItems[tmplItem.parent.key + keySuffix] || tmplItem.parent ));
            }
        }
    }

    // ---- Helper functions for template item ----

    function tiCalls( content, tmpl, data, options ) {
        if ( !content ) {
            return stack.pop();
        }
        stack.push({ _: content, tmpl: tmpl, item:this, data: data, options: options });
    }

    function tiNest( tmpl, data, options ) {
        // nested template, using {{tmpl}} tag
        return jQuery.tmpl( jQuery.template( tmpl ), data, options, this );
    }

    function tiWrap( call, wrapped ) {
        // nested template, using {{wrap}} tag
        var options = call.options || {};
        options.wrapped = wrapped;
        // Apply the template, which may incorporate wrapped content,
        return jQuery.tmpl( jQuery.template( call.tmpl ), call.data, options, call.item );
    }

    function tiHtml( filter, textOnly ) {
        var wrapped = this._wrap;
        return jQuery.map(
            jQuery( jQuery.isArray( wrapped ) ? wrapped.join("") : wrapped ).filter( filter || "*" ),
            function(e) {
                return textOnly ?
                    e.innerText || e.textContent :
                    e.outerHTML || outerHtml(e);
            });
    }

    function tiUpdate() {
        var coll = this.nodes;
        jQuery.tmpl( null, null, null, this).insertBefore( coll[0] );
        jQuery( coll ).remove();
    }
})( jQuery );


/**
 *  ttevents.js
 * 	author: fugang 
 * 	date: 2014-04-02
 */

;(function () {
	
	var eventSplitter = /\s+/;

	var Events = {

		// Bind one or more space separated events, `events`, to a `callback`
		// function. Passing `"all"` will bind the callback to all events fired.
		on: function(events, callback, context) {
		  var calls, event, list;
		  if (!callback) return this;

		  events = events.split(eventSplitter);
		  calls = this._callbacks || (this._callbacks = {});

		  while (event = events.shift()) {
		    list = calls[event] || (calls[event] = []);
		    list.push(callback, context);
		  }

		  return this;
		},

		// Remove one or many callbacks. If `context` is null, removes all callbacks
		// with that function. If `callback` is null, removes all callbacks for the
		// event. If `events` is null, removes all bound callbacks for all events.
		off: function(events, callback, context) {
		  var event, calls, list, i;

		  // No events, or removing *all* events.
		  if (!(calls = this._callbacks)) return this;
		  if (!(events || callback || context)) {
		    delete this._callbacks;
		    return this;
		  }

		  events = events ? events.split(eventSplitter) : _.keys(calls);

		  // Loop through the callback list, splicing where appropriate.
		  while (event = events.shift()) {
		    if (!(list = calls[event]) || !(callback || context)) {
		      delete calls[event];
		      continue;
		    }

		    for (i = list.length - 2; i >= 0; i -= 2) {
		      if (!(callback && list[i] !== callback || context && list[i + 1] !== context)) {
		        list.splice(i, 2);
		      }
		    }
		  }

		  return this;
		},

		// Trigger one or many events, firing all bound callbacks. Callbacks are
		// passed the same arguments as `trigger` is, apart from the event name
		// (unless you're listening on `"all"`, which will cause your callback to
		// receive the true name of the event as the first argument).
		trigger: function(events) {
		  var event, calls, list, i, length, args, all, rest;
		  if (!(calls = this._callbacks)) return this;

		  rest = [];
		  events = events.split(eventSplitter);

		  // Fill up `rest` with the callback arguments.  Since we're only copying
		  // the tail of `arguments`, a loop is much faster than Array#slice.
		  for (i = 1, length = arguments.length; i < length; i++) {
		    rest[i - 1] = arguments[i];
		  }

		  // For each event, walk through the list of callbacks twice, first to
		  // trigger the event, then to trigger any `"all"` callbacks.
		  while (event = events.shift()) {
		    // Copy callback lists to prevent modification.
		    if (all = calls.all) all = all.slice();
		    if (list = calls[event]) list = list.slice();

		    // Execute event callbacks.
		    if (list) {
		      for (i = 0, length = list.length; i < length; i += 2) {
		        list[i].apply(list[i + 1] || this, rest);
		      }
		    }

		    // Execute "all" callbacks.
		    if (all) {
		      args = [event].concat(rest);
		      for (i = 0, length = all.length; i < length; i += 2) {
		        all[i].apply(all[i + 1] || this, args);
		      }
		    }
		  }

		  return this;
		}

	};

	// Aliases for backwards compatibility.
	Events.bind   = Events.on;
	Events.unbind = Events.off;

	

	var delegateEventSplitter = /^(\S+)\s*(.*)$/;
	var tt = {
		/**
         * Returns the type of the given variable in string format. List of possible values are:
         *
         * - `undefined`: If the given value is `undefined`
         * - `null`: If the given value is `null`
         * - `string`: If the given value is a string
         * - `number`: If the given value is a number
         * - `boolean`: If the given value is a boolean value
         * - `date`: If the given value is a `Date` object
         * - `function`: If the given value is a function reference
         * - `object`: If the given value is an object
         * - `array`: If the given value is an array
         * - `regexp`: If the given value is a regular expression
         * - `element`: If the given value is a DOM Element
         * - `textnode`: If the given value is a DOM text node and contains something other than whitespace
         * - `whitespace`: If the given value is a DOM text node and contains only whitespace
         *
         * @param {Object} value
         * @return {String}
         * @markdown
         */
        typeOf: function(value) {
            if (value === null) {
                return 'null';
            }

            var type = typeof value;

            if (type === 'undefined' || type === 'string' || type === 'number' || type === 'boolean') {
                return type;
            }

            var typeToString = toString.call(value);

            switch(typeToString) {
                case '[object Array]':
                    return 'array';
                case '[object Date]':
                    return 'date';
                case '[object Boolean]':
                    return 'boolean';
                case '[object Number]':
                    return 'number';
                case '[object RegExp]':
                    return 'regexp';
            }

            if (type === 'function') {
                return 'function';
            }

            if (type === 'object') {
                if (value.nodeType !== undefined) {
                    if (value.nodeType === 3) {
                        return (/\S/).test(value.nodeValue) ? 'textnode' : 'whitespace';
                    }
                    else {
                        return 'element';
                    }
                }

                return 'object';
            }

            throw new Error('Failed to determine the type of the specified value "' + value + '". This is most likely a bug.');
        },
        /**
         * Returns true if the passed value is a JavaScript Function, false otherwise.
         * @param {Object} value The value to test
         * @return {Boolean}
         * @method
         */
        isFunction:
	        // Safari 3.x and 4.x returns 'function' for typeof <NodeList>, hence we need to fall back to using
	        // Object.prorotype.toString (slower)
	        (typeof document !== 'undefined' && typeof document.getElementsByTagName('body') === 'function') ? function(value) {
	            return toString.call(value) === '[object Function]';
	        } : function(value) {
	            return typeof value === 'function';
        },
		//
	    // *{"event selector": "callback"}*
	    //
	    //     {
	    //       'mousedown .title':  'edit',
	    //       'click .button':     'save'
	    //       'click .open':       function(e) { ... }
	    //     }
	    //
	    // This only works for delegate-able events: not `focus`, `blur`, and
	    // not `change`, `submit`, and `reset` in Internet Explorer.
	    delegateEvents: function(oparent) {
	    	events = oparent.events;
	    	if (!(events || (events = LT.result(this, 'events')))) return;
	    	this.$el = oparent.$el;
	    	this.undelegateEvents();

	    	var _this = oparent;
	    	for (var key in events) {
	    		var method = events[key];
	    		if (LT.typeOf(method) !== 'function') method = oparent[events[key]];
	    		if (!method) throw new Error('Method "' + events[key] + '" does not exist');

	    		var match = key.match(delegateEventSplitter);
	    		var eventName = match[1], selector = match[2];
	    		//method = this[method];
	    		eventName += '.delegateEvents' + this.cid;
	    		if (selector === '') {
	    			this.$el.bind(eventName, (function (method) {
	    				return function (event) {
	    					method.apply(_this,arguments);
		    				// method.call(_this, event);
		    			}
	    			})(method));
	    		} else {
	    			this.$el.delegate(selector, eventName, (function (method) {
	    				return function (event) {
	    					method.apply(_this,arguments);
		    				//method.call(_this, event);
		    			}
	    			})(method));
	    		}
	    	}
	    },

	    undelegateEvents: function() {
	    	this.$el.unbind('.delegateEvents' + this.cid);
	    },
	    getStrLen: function (i) {
	        return Math.ceil(String(i).replace(/[^\x00-\xff]/g, "ci").length / 2)
	    },
	    resizeImg: function (i, n, t, o) {
	        if (!i) return "";
	        var e, a, r = t || 80,
	            s = o || 60,
	            l = 0,
	            h = 0;
	        i / n > r / s ? (e = s / n * i, a = s, l = (r - e) / 2) : (e = r, a = r / i * n, h = (s - a) / 2);
	        var c = "width:" + e + "px;height:" + a + "px;left:" + l + "px;top:" + h + "px";
	        return c
	    },
	    scrollTo: function (i, n) {
	        var t = i.offset().top - ("undefined" == typeof n ? 60 : n);
	        $("html,body").animate({
	            scrollTop: t
	        }, 300)
	    }
	};
	
	LT = tt || {};
	LT.Events = Events;

	/**
     * 继承
     * @param  {class} childClass 子类
     * @param  {class} superClass 父类
     * @return {class} 
     */
    LT.extend = function (childClass, superClass) {
        var F = function(){};   
        var spp = F.prototype = superClass.prototype;
        childClass.prototype = new F();
        childClass.prototype.constructor = childClass;
        childClass.prototype.superclass = spp;
        childClass.superclass = spp;
        return childClass;
    };
	/**
     * 获取一个对象中的属性值
     * @param  {object} object  
     * @param  {string} property
     * @return {value}
     */
    LT.result = function(object, property) {
        if (object == null) return null;
        var value = object[property];
        return LT.typeOf(value) === 'function' ? value.call(object) : value;
    };
})();
/**
 *  ttevents.js
 * 	author: fugang 
 * 	date: 2014-04-02
 */

;(function () {
	site_domain = "http://www.davcai.com/";//www.davcai.com
	LT = LT || {};
	LT.Settings = $.extend({}, {
		/**
		 * 资源目录名
		 */
		ASSETS : "",

		/**
		 * 静态资源
		 * @type {Object}
		 */
		RESOURCE : {
			//默认头像
			DEFAULT_HEADER 		: 'http://www.davcai.com/images/default_face.jpg',
			DEFAULT_VIDEO 		: '',
			DEFAULT_LINKICON 	: '',
			loading8 			: '',
			loading16 			: '',
			DEFAULT_IE 			:  ''
		},

		/**
		 * 接口集合
		 * @type {Object}
		 */
		URLS : {
			home : {
				aj_fb_selector 		: site_domain+'aj_fb_selector.do',
				aj_bb_selector 		: site_domain+'aj_bb_selector.do',
				aj_bjdc_selector	: site_domain+'aj_bjdc_selector'
			},
			weibo : {
				loadRecommends 		: site_domain+'loadRecommends',
				loadDiscussWeibo 	: site_domain+'loadDiscussWeibo',
				loadNews 			: site_domain+'loadNews',
				loadPost 			: site_domain+'loadPost',
				getfollowingsids 	: site_domain+'aj_get_followingsids',
				weibo_new_recommends: site_domain+'weibo_new_recommends',
				weibo_new_discuss 	: site_domain+'weibo_new_discuss',
				weibo_new_news 		: site_domain+'weibo_new_news',
				weibo_new_posts 	: site_domain+'weibo_new_posts',
				newPostTimer 		: site_domain+'newPostTimer',
				newRecTimer 		: site_domain+'newRecTimer',
				newMatchTimer 		: site_domain+'newMatchTimer',
				newNewsTimer 		: site_domain+'newNewsTimer',
				newDiscussWeiboTimer: site_domain+'newDiscussWeiboTimer',
				publish				: site_domain+'publish'
			},
			match_weibo : {
				loadRealMatchPost 		: site_domain+'loadRealMatchPost',
				loadMatchDiscussPost 	: site_domain+'loadMatchDiscussPost',
				loadMatchsNews 			: site_domain+'loadMatchsNews',
				loadMatchPost 			: site_domain+'loadMatchPost',
				load_new_matchs_realMatch: site_domain+'load_new_matchs_realMatch',
				load_new_matchs_discuss 	: site_domain+'load_new_matchs_discuss',
				load_new_matchs_news 		: site_domain+'load_new_matchs_news',
				load_new_matchs_posts 	: site_domain+'load_new_matchs_posts',
				newMatchPostTimer 		: site_domain+'newMatchPostTimer',
				newRealMatchPostTimer 		: site_domain+'newRealMatchPostTimer',
				newMatchNewsTimer 		: site_domain+'newMatchNewsTimer',
				newMatchDiscussTimer 		: site_domain+'newMatchDiscussTimer'
			},
			user_relationship : {
				interest_user 		: site_domain+'interest_user?size=60'
			},
			winningnews:{
				winningnew          : site_domain+'ajax/ajaxLoadWinningNews.do'
			},
			rankinglist:{
				spf_shenglv_7day    :site_domain+'ajax_day7ShengLv', //推荐页胜平负 7日胜率
				spf_shenglv_50match :site_domain+'ajax_match50ShengLv', //推荐页胜平负 50场胜率
				spf_yingli_7day     :site_domain+'/ajax_day7YingLi',  //推荐页胜平负 7日盈利
				spf_yingli_50match  :site_domain+'/ajax_match50YingLv', //推荐页胜平负 50场盈利
				showscheme_shenglv_7day :site_domain+'/newwinlist/ajax_showDay7SchemeWinShengLv', //晒单页面 7日晒单胜率
				showscheme_shenglv_50match:site_domain+'/newwinlist/ajax_showMatch50SchemeWinShengLv', //晒单页面 50场晒单胜率
				followscheme_shenglv_7day :site_domain+'/newwinlist/ajax_showDay7FollowSchemeWinYingliLv',//晒单页 跟单7日胜率
				followscheme_shenglv_50match:site_domain+'/newwinlist/ajax_showMatch50FollowShemeWinYingliLv',//晒单页 跟单50场胜率
				showscheme_prize_7day :site_domain+'/newwinlist/ajax_showDay7SchemeWin',//晒单页 晒单助人 7日
				showscheme_prize_50match :site_domain+'/newwinlist/ajax_showMatch50SchemeWin',//晒单页 晒单助人 50单
				followscheme_win_7day :site_domain+'/newwinlist/ajax_showDay7FollowSchemeWin',//晒单页 跟单中奖  7日
				followscheme_win_50match :site_domain+'/newwinlist/ajax_showMatch50FollowSchemeWin' //晒单页 跟单中奖  50单
				
			},
			bonus:{
				bonuslist           :site_domain+'/prize/ajax_winprize' //中奖喜报页面中奖排行版
			},
			active:{
				activeuser          :site_domain+'/activeuser/ajax_activeuser'//活跃用户
			},
			comment:{
				loadComments 		:site_domain+'list_cmnts',
				postComment 		:site_domain+'post_cmnt'
			},
			like:{
				like				:site_domain+'like',
				unlike				:site_domain+'unLike',
				ajaxLoadFavoriate	:site_domain+'ajaxLoadFavoriate'
			},
			marrow:{
				ajaxWinList				:site_domain+'ajax-winning-list',
				ajaxFollowingWinList	:site_domain+'ajax-following-winnings',
				ajaxHotDiscussList		:site_domain+'ajax-hot-discuss',
				ajaxLatestPublishList	:site_domain+'ajax-latest-publish'
			},
			betRecord:{
				ajaxUserBetRecord : site_domain+'/bet/userBetRecord',
				ajaxViewBetScheme : site_domain+'/bet/scheme',
			},
			recommend:{
				ajaxJCZQ	:site_domain+'recommend/ajax_JCZQ',
				ajaxJCLQ	:site_domain+'recommend/ajax_JCLQ',
				ajaxBJDC	:site_domain+'recommend/ajax_BJDC',
				ajaxALL		:site_domain+'recommend/ajax_ALL',
			},
			realfollow:{
				ajaxJCZQ	:site_domain+'realfollow/ajax_JCZQ',
				ajaxJCLQ	:site_domain+'realfollow/ajax_JCLQ',
				ajaxBJDC	:site_domain+'realfollow/ajax_BJDC',
				ajaxALL		:site_domain+'realfollow/ajax_ALL',
			}
		},
		
		//工程版本号
		VERSION : "",
		
		//网站访问连接
		LT_BASE_URL : site_domain,
	});
})();
(function ($){
	var followTitle = '加关注';
	var unfollowTitle = '取消关注';
	var followView = '+加关注';
	var unfollowView = '取消关注';
	
	var addFollowBtn = function ($el,option){
		this.options = $.extend({},$.fn.addFollowBtn.defaults,option);
		this.$el = $el;
		this.$tmpl = $("#follow-btn-tmpl");
		this.init();
	}
	addFollowBtn.prototype = {
		init : function (){
			this.render();
			this.bind();
		},
		render : function (){
			var html=$.mustache(this.$tmpl.html(),{
				title: this.options.flag == 'follow'? followTitle:unfollowTitle,
				view: this.options.flag == 'follow'? followView:unfollowView
			});
			this.$el.html(html);
		},
		bind : function (){
			var $this = this;
			this.$el.find(".addFriendBtn").click(function (){
				if($this.options.flag == 'follow'){
					var userId = $this.options.userId;
					if(logind == false){
						//弹出登录框
						displayLogin();
						
					}
					$.post("/ajax_rltship_follow",{followIds:userId}, function(data , e) {
						if (data != null) {
							if (data.success) {
								$this.options.flag = 'unfollow';
								$this.$el.addFollowBtn($this.options);
								//回调成功后的方法
								$this.options.success($this.$el,$this.options.display);
							} else {
								//alert(data.desc);
								if(data.resultCode == "notlogin"){
									//window.location.href="http://www.davcai.com/";
									displayLogin();
								}
								$this.options.fail($this.$el);
							}
						}
					}, 'json');
				} else {
					var userId = $this.options.userId;
					$.post("/ajax_rltship_unfollow",{unfollowIds:userId}, function(data , e) {
						if (data != null) {
							if (data.success) {
								$this.options.flag = 'follow';
								$this.$el.addFollowBtn($this.options);
							} else {
								//alert(data.desc);
								if(data.resultCode == "notlogin"){
									//window.location.href="http://www.davcai.com/";
									displayLogin();
								}
							}
						}
					}, 'json');
				}
			});
		},
		refresh : function (){
			this.render();
			this.bind();
		}
	}
	$.fn.addFollowBtn = function (option) {
		return this.each(function() {
			var $this = $(this),
				data = $this.data('addFollowBtn');
	          if (!data) {
	        	  $this.data('addFollowBtn', (data = new addFollowBtn($this, option)));
	          } else {
	        	  data.refresh(option);
	          }
        });
	};
	$.fn.addFollowBtn.defaults = {
		flag:'follow',
		userId:'',
		success : function ($el){
			return "";
		},
		fail : function ($el){
			return "";
		}
	};
})(window.jQuery);
$(function (){
	$("#interest_user_list").interestUser({"id":"#interest_user_list","listSize":3,"hidden":true,"winningList":true,"_url":LT.Settings.URLS.user_relationship.interest_user});
	$("#interest_user_list").parent().find(".change a").click(function (){
		$("#interest_user_list").interestUser({
			changeUser:true
		});
	});
	$("#winning_user_list").interestUser({"id":"#winning_user_list","listSize":3,"hidden":false,"_url":LT.Settings.URLS.winningnews.winningnew});
	isLoginForAddBtn();
});

(function ($){
	var interestUser = function ($el,option){
		this.options = $.extend({},$.fn.interestUser.defaults,option);
		this.$el = $el;
		this.$tmpl = $("#interest-user-tmpl");
		this.$loading = $el.find(".loading");
		this.init();
	};
	interestUser.prototype = {
		init:function (){
			var $this = this;
			$.ajax({  
				type : "post",  
		        url : $this.options._url,//eval($($this.options.id).attr("_url")),  
		        dataType : 'json',
		        success : function(data){
		        	$this.data = data;  
		        	$this.render();
		        	$this.bind();
		        }  
		    });
		},
		render:function (){
			this.$loading.hide();
			if(this.data.results.length < 1){
				this.$el.html('<li style=" text-align:center">'//position: relative;
						+ '没有啦'
						+ '</li>');
				return ;
			}
			var $this_=this;
			//解析结果
			var afterAnalysis=function(){
				var results=$this_.data.results;
				
				for(var i=0;i<results.length;i++){
					//转换为中文显示
					if(results[i].lotteryId == "JCZQ"){
						
						results[i].lotteryId="竞彩足球";
					}else if(results[i].lotteryId == "JCLQ"){
						
						results[i].lotteryId="竞彩篮球";
					}else if(results[i].lotteryId == "BJDC"){
						
						results[i].lotteryId="北京单场";
					}
					//转换名称率
					if(results[i].ratio){
						var hit=results[i].ratio>0?parseInt(results[i].ratio * 100)+"%":"";
						results[i].ratio=hit;
						
					}
					if(results[i].winRate){
						var winRate=parseInt(results[i].winRate*100)+"%";
						results[i].winRate=winRate;
					}
					
					if(results[i].bonus_){
						var bonus=parseInt(results[i].bonus_);
						if(bonus>10000){
							var bwan=parseInt(bonus/10000);
							var bqian=parseInt(bonus%10000/1000);
							results[i].bonus_=bwan+"."+bqian+"万元"
						}else{
							results[i].bonus_=bonus+"元";
							
						}
						
					}
					if(results[i].totalBonus){
						var totalBonus=results[i].totalBonus;
						totalBonus=parseInt(totalBonus);

						if(totalBonus>10000){
							var wan=parseInt(totalBonus/10000);
							var qian=parseInt(totalBonus%10000/1000);
							results[i].totalBonus=wan+"."+qian+"万元"
						}else{
							results[i].totalBonus=parseInt(totalBonus)+"元";
						}
						
					}
				}
				
				return results;
			}
			var html = $.mustache(this.$tmpl.html(),{
				users:afterAnalysis(),
				certificationFlag:function (){
					if(this.certificationType == 1){
						return $("#certification-flag-tmpl").html();
					}
				}
			});
			this.$el.append(html);//所有li插入推荐列表
			var $lis = this.$el.find("li");
			for(var i=0; i<$lis.length; i++){//隐藏索引超过maxSize的部分
				if(i >= this.options.maxSize){
					$($lis[i]).hide();
				}
			}
		},
		bind:function (){			
			var $el = this.$el;
			var $hidden=this.options.hidden;
			var maxSize = this.options.maxSize;
			var $followContainer = this.$el.find("[_id='follow-container-div']");
			//添加“加关注”按钮
			$followContainer.each(function (){
				
				var $this = $(this);
				$this.addFollowBtn({
					flag:'follow',
					display:$hidden,//关注后是否隐藏
					userId:$this.attr("_userId"),
					success:function ($this,$op){ //关注成功回调
						var $li = $this.closest("li");
						var $next = $li.next();
						var $hidden=$op;
						if($hidden == true){
							$li.fadeOut("slow",function (){
								$li.remove();
								var $lis = $el.find("li");
								if($lis.length < 1){
									$el.html('<li style="text-align:center">'//position: relative;
											+ '没有了 '
											+ '</li>');
								} else if($lis.length > maxSize) {
									for(var i=0;i<$lis.length;i++){
										var _li = $($lis[i]);
										if(!_li.is(':visible')){
											break;
										}
									}
									if($next.find(".interest-name").text() ==
										$($lis[i]).find(".interest-name").text()){
										$next.show();
									} else {
										$next.before($($lis[i]).show());
									}
								}
							});
							
						}
						
					}
				});
			});
		},
		refresh:function(option){
			this.options = $.extend({},this.options,option);
			if(this.options.changeUser){
				var lis = this.$el.find('li');
				if(lis.length <=3){
					return '';
				}
				var i=0;
				for(;i<lis.length;i++){
					var $li = $(lis[i]);
					if($li.is(':visible')){
						break;
					}
				}
				i+=3;//需要显示的第一个用户索引
				var j=0;//已经选到的用户数
				var u = new Array();
				for(;i<lis.length && j < 3;i++,j++){
					u.push(i);
				}
				if(u.length <3){
					for(i=0;i<lis.length;i++){
						u.push(i);
						if(u.length >=3){
							break;
						}
					}
				}
				lis.each(function (){
					$(this).hide();
				});
				for(i=0;i<u.length;i++){
					$(lis[u[i]]).show();
				}
			}
		}
	}
	$.fn.interestUser = function (option){
		if(option.listSize){
			$.fn.interestUser.defaults={maxSize:option.listSize};	
		}
		return this.each(function() {
			var $this = $(this),
				data = $this.data('interestUser');
	          if (!data) {
	        	  $this.data('interestUser', (data = new interestUser($this, option)));
	          } else {
	        	  data.refresh(option);
	          }
        });
	};
	$.fn.interestUser.defaults = {
		maxSize:6
	};
})(window.jQuery);

/*
 * Copyright (c) 2008-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// Namespaces for the cometd implementation
this.org = this.org || {};
org.cometd = {};

org.cometd.JSON = {};
org.cometd.JSON.toJSON = org.cometd.JSON.fromJSON = function(object)
{
    throw 'Abstract';
};

org.cometd.Utils = {};

org.cometd.Utils.isString = function(value)
{
    if (value === undefined || value === null)
    {
        return false;
    }
    return typeof value === 'string' ||  value instanceof String;
};

org.cometd.Utils.isArray = function(value)
{
    if (value === undefined || value === null)
    {
        return false;
    }
    return value instanceof Array;
};

/**
 * Returns whether the given element is contained into the given array.
 * @param element the element to check presence for
 * @param array the array to check for the element presence
 * @return the index of the element, if present, or a negative index if the element is not present
 */
org.cometd.Utils.inArray = function(element, array)
{
    for (var i = 0; i < array.length; ++i)
    {
        if (element === array[i])
        {
            return i;
        }
    }
    return -1;
};

org.cometd.Utils.setTimeout = function(cometd, funktion, delay)
{
    return window.setTimeout(function()
    {
        try
        {
            cometd._debug('Invoking timed function', funktion);
            funktion();
        }
        catch (x)
        {
            cometd._debug('Exception invoking timed function', funktion, x);
        }
    }, delay);
};

org.cometd.Utils.clearTimeout = function(timeoutHandle)
{
    window.clearTimeout(timeoutHandle);
};

/**
 * A registry for transports used by the CometD object.
 */
org.cometd.TransportRegistry = function()
{
    var _types = [];
    var _transports = {};

    this.getTransportTypes = function()
    {
        return _types.slice(0);
    };

    this.findTransportTypes = function(version, crossDomain, url)
    {
        var result = [];
        for (var i = 0; i < _types.length; ++i)
        {
            var type = _types[i];
            if (_transports[type].accept(version, crossDomain, url) === true)
            {
                result.push(type);
            }
        }
        return result;
    };

    this.negotiateTransport = function(types, version, crossDomain, url)
    {
        for (var i = 0; i < _types.length; ++i)
        {
            var type = _types[i];
            for (var j = 0; j < types.length; ++j)
            {
                if (type === types[j])
                {
                    var transport = _transports[type];
                    if (transport.accept(version, crossDomain, url) === true)
                    {
                        return transport;
                    }
                }
            }
        }
        return null;
    };

    this.add = function(type, transport, index)
    {
        var existing = false;
        for (var i = 0; i < _types.length; ++i)
        {
            if (_types[i] === type)
            {
                existing = true;
                break;
            }
        }

        if (!existing)
        {
            if (typeof index !== 'number')
            {
                _types.push(type);
            }
            else
            {
                _types.splice(index, 0, type);
            }
            _transports[type] = transport;
        }

        return !existing;
    };

    this.find = function(type)
    {
        for (var i = 0; i < _types.length; ++i)
        {
            if (_types[i] === type)
            {
                return _transports[type];
            }
        }
        return null;
    };

    this.remove = function(type)
    {
        for (var i = 0; i < _types.length; ++i)
        {
            if (_types[i] === type)
            {
                _types.splice(i, 1);
                var transport = _transports[type];
                delete _transports[type];
                return transport;
            }
        }
        return null;
    };

    this.clear = function()
    {
        _types = [];
        _transports = {};
    };

    this.reset = function()
    {
        for (var i = 0; i < _types.length; ++i)
        {
            _transports[_types[i]].reset();
        }
    };
};

/**
 * Base object with the common functionality for transports.
 */
org.cometd.Transport = function()
{
    var _type;
    var _cometd;

    /**
     * Function invoked just after a transport has been successfully registered.
     * @param type the type of transport (for example 'long-polling')
     * @param cometd the cometd object this transport has been registered to
     * @see #unregistered()
     */
    this.registered = function(type, cometd)
    {
        _type = type;
        _cometd = cometd;
    };

    /**
     * Function invoked just after a transport has been successfully unregistered.
     * @see #registered(type, cometd)
     */
    this.unregistered = function()
    {
        _type = null;
        _cometd = null;
    };

    this._debug = function()
    {
        _cometd._debug.apply(_cometd, arguments);
    };

    this._mixin = function()
    {
        return _cometd._mixin.apply(_cometd, arguments);
    };

    this.getConfiguration = function()
    {
        return _cometd.getConfiguration();
    };

    this.getAdvice = function()
    {
        return _cometd.getAdvice();
    };

    this.setTimeout = function(funktion, delay)
    {
        return org.cometd.Utils.setTimeout(_cometd, funktion, delay);
    };

    this.clearTimeout = function(handle)
    {
        org.cometd.Utils.clearTimeout(handle);
    };

    /**
     * Converts the given response into an array of bayeux messages
     * @param response the response to convert
     * @return an array of bayeux messages obtained by converting the response
     */
    this.convertToMessages = function (response)
    {
        if (org.cometd.Utils.isString(response))
        {
            try
            {
                return org.cometd.JSON.fromJSON(response);
            }
            catch(x)
            {
                this._debug('Could not convert to JSON the following string', '"' + response + '"');
                throw x;
            }
        }
        if (org.cometd.Utils.isArray(response))
        {
            return response;
        }
        if (response === undefined || response === null)
        {
            return [];
        }
        if (response instanceof Object)
        {
            return [response];
        }
        throw 'Conversion Error ' + response + ', typeof ' + (typeof response);
    };

    /**
     * Returns whether this transport can work for the given version and cross domain communication case.
     * @param version a string indicating the transport version
     * @param crossDomain a boolean indicating whether the communication is cross domain
     * @return true if this transport can work for the given version and cross domain communication case,
     * false otherwise
     */
    this.accept = function(version, crossDomain, url)
    {
        throw 'Abstract';
    };

    /**
     * Returns the type of this transport.
     * @see #registered(type, cometd)
     */
    this.getType = function()
    {
        return _type;
    };

    this.send = function(envelope, metaConnect)
    {
        throw 'Abstract';
    };

    this.reset = function()
    {
        this._debug('Transport', _type, 'reset');
    };

    this.abort = function()
    {
        this._debug('Transport', _type, 'aborted');
    };

    this.toString = function()
    {
        return this.getType();
    };
};

org.cometd.Transport.derive = function(baseObject)
{
    function F() {}
    F.prototype = baseObject;
    return new F();
};

/**
 * Base object with the common functionality for transports based on requests.
 * The key responsibility is to allow at most 2 outstanding requests to the server,
 * to avoid that requests are sent behind a long poll.
 * To achieve this, we have one reserved request for the long poll, and all other
 * requests are serialized one after the other.
 */
org.cometd.RequestTransport = function()
{
    var _super = new org.cometd.Transport();
    var _self = org.cometd.Transport.derive(_super);
    var _requestIds = 0;
    var _metaConnectRequest = null;
    var _requests = [];
    var _envelopes = [];

    function _coalesceEnvelopes(envelope)
    {
        while (_envelopes.length > 0)
        {
            var envelopeAndRequest = _envelopes[0];
            var newEnvelope = envelopeAndRequest[0];
            var newRequest = envelopeAndRequest[1];
            if (newEnvelope.url === envelope.url &&
                    newEnvelope.sync === envelope.sync)
            {
                _envelopes.shift();
                envelope.messages = envelope.messages.concat(newEnvelope.messages);
                this._debug('Coalesced', newEnvelope.messages.length, 'messages from request', newRequest.id);
                continue;
            }
            break;
        }
    }

    function _transportSend(envelope, request)
    {
        this.transportSend(envelope, request);
        request.expired = false;

        if (!envelope.sync)
        {
            var maxDelay = this.getConfiguration().maxNetworkDelay;
            var delay = maxDelay;
            if (request.metaConnect === true)
            {
                delay += this.getAdvice().timeout;
            }

            this._debug('Transport', this.getType(), 'waiting at most', delay, 'ms for the response, maxNetworkDelay', maxDelay);

            var self = this;
            request.timeout = this.setTimeout(function()
            {
                request.expired = true;
                var errorMessage = 'Request ' + request.id + ' of transport ' + self.getType() + ' exceeded ' + delay + ' ms max network delay';
                var failure = {
                    reason: errorMessage
                };
                var xhr = request.xhr;
                failure.httpCode = self.xhrStatus(xhr);
                self.abortXHR(xhr);
                self._debug(errorMessage);
                self.complete(request, false, request.metaConnect);
                envelope.onFailure(xhr, envelope.messages, failure);
            }, delay);
        }
    }

    function _queueSend(envelope)
    {
        var requestId = ++_requestIds;
        var request = {
            id: requestId,
            metaConnect: false
        };

        // Consider the metaConnect requests which should always be present
        if (_requests.length < this.getConfiguration().maxConnections - 1)
        {
            _requests.push(request);
            _transportSend.call(this, envelope, request);
        }
        else
        {
            this._debug('Transport', this.getType(), 'queueing request', requestId, 'envelope', envelope);
            _envelopes.push([envelope, request]);
        }
    }

    function _metaConnectComplete(request)
    {
        var requestId = request.id;
        this._debug('Transport', this.getType(), 'metaConnect complete, request', requestId);
        if (_metaConnectRequest !== null && _metaConnectRequest.id !== requestId)
        {
            throw 'Longpoll request mismatch, completing request ' + requestId;
        }

        // Reset metaConnect request
        _metaConnectRequest = null;
    }

    function _complete(request, success)
    {
        var index = org.cometd.Utils.inArray(request, _requests);
        // The index can be negative if the request has been aborted
        if (index >= 0)
        {
            _requests.splice(index, 1);
        }

        if (_envelopes.length > 0)
        {
            var envelopeAndRequest = _envelopes.shift();
            var nextEnvelope = envelopeAndRequest[0];
            var nextRequest = envelopeAndRequest[1];
            this._debug('Transport dequeued request', nextRequest.id);
            if (success)
            {
                if (this.getConfiguration().autoBatch)
                {
                    _coalesceEnvelopes.call(this, nextEnvelope);
                }
                _queueSend.call(this, nextEnvelope);
                this._debug('Transport completed request', request.id, nextEnvelope);
            }
            else
            {
                // Keep the semantic of calling response callbacks asynchronously after the request
                var self = this;
                this.setTimeout(function()
                {
                    self.complete(nextRequest, false, nextRequest.metaConnect);
                    var failure = {
                        reason: 'Previous request failed'
                    };
                    var xhr = nextRequest.xhr;
                    failure.httpCode = self.xhrStatus(xhr);
                    nextEnvelope.onFailure(xhr, nextEnvelope.messages, failure);
                }, 0);
            }
        }
    }

    _self.complete = function(request, success, metaConnect)
    {
        if (metaConnect)
        {
            _metaConnectComplete.call(this, request);
        }
        else
        {
            _complete.call(this, request, success);
        }
    };

    /**
     * Performs the actual send depending on the transport type details.
     * @param envelope the envelope to send
     * @param request the request information
     */
    _self.transportSend = function(envelope, request)
    {
        throw 'Abstract';
    };

    _self.transportSuccess = function(envelope, request, responses)
    {
        if (!request.expired)
        {
            this.clearTimeout(request.timeout);
            this.complete(request, true, request.metaConnect);
            if (responses && responses.length > 0)
            {
                envelope.onSuccess(responses);
            }
            else
            {
                envelope.onFailure(request.xhr, envelope.messages, {
                    httpCode: 204
                });
            }
        }
    };

    _self.transportFailure = function(envelope, request, failure)
    {
        if (!request.expired)
        {
            this.clearTimeout(request.timeout);
            this.complete(request, false, request.metaConnect);
            envelope.onFailure(request.xhr, envelope.messages, failure);
        }
    };

    function _metaConnectSend(envelope)
    {
        if (_metaConnectRequest !== null)
        {
            throw 'Concurrent metaConnect requests not allowed, request id=' + _metaConnectRequest.id + ' not yet completed';
        }

        var requestId = ++_requestIds;
        this._debug('Transport', this.getType(), 'metaConnect send, request', requestId, 'envelope', envelope);
        var request = {
            id: requestId,
            metaConnect: true
        };
        _transportSend.call(this, envelope, request);
        _metaConnectRequest = request;
    }

    _self.send = function(envelope, metaConnect)
    {
        if (metaConnect)
        {
            _metaConnectSend.call(this, envelope);
        }
        else
        {
            _queueSend.call(this, envelope);
        }
    };

    _self.abort = function()
    {
        _super.abort();
        for (var i = 0; i < _requests.length; ++i)
        {
            var request = _requests[i];
            this._debug('Aborting request', request);
            this.abortXHR(request.xhr);
        }
        if (_metaConnectRequest)
        {
            this._debug('Aborting metaConnect request', _metaConnectRequest);
            this.abortXHR(_metaConnectRequest.xhr);
        }
        this.reset();
    };

    _self.reset = function()
    {
        _super.reset();
        _metaConnectRequest = null;
        _requests = [];
        _envelopes = [];
    };

    _self.abortXHR = function(xhr)
    {
        if (xhr)
        {
            try
            {
                xhr.abort();
            }
            catch (x)
            {
                this._debug(x);
            }
        }
    };

    _self.xhrStatus = function(xhr)
    {
        if (xhr)
        {
            try
            {
                return xhr.status;
            }
            catch (x)
            {
                this._debug(x);
            }
        }
        return -1;
    };

    return _self;
};

org.cometd.LongPollingTransport = function()
{
    var _super = new org.cometd.RequestTransport();
    var _self = org.cometd.Transport.derive(_super);
    // By default, support cross domain
    var _supportsCrossDomain = true;

    _self.accept = function(version, crossDomain, url)
    {
        return _supportsCrossDomain || !crossDomain;
    };

    _self.xhrSend = function(packet)
    {
        throw 'Abstract';
    };

    _self.transportSend = function(envelope, request)
    {
        this._debug('Transport', this.getType(), 'sending request', request.id, 'envelope', envelope);

        var self = this;
        try
        {
            var sameStack = true;
            request.xhr = this.xhrSend({
                transport: this,
                url: envelope.url,
                sync: envelope.sync,
                headers: this.getConfiguration().requestHeaders,
                body: org.cometd.JSON.toJSON(envelope.messages),
                onSuccess: function(response)
                {
                    self._debug('Transport', self.getType(), 'received response', response);
                    var success = false;
                    try
                    {
                        var received = self.convertToMessages(response);
                        if (received.length === 0)
                        {
                            _supportsCrossDomain = false;
                            self.transportFailure(envelope, request, {
                                httpCode: 204
                            });
                        }
                        else
                        {
                            success = true;
                            self.transportSuccess(envelope, request, received);
                        }
                    }
                    catch(x)
                    {
                        self._debug(x);
                        if (!success)
                        {
                            _supportsCrossDomain = false;
                            var failure = {
                                exception: x
                            };
                            failure.httpCode = self.xhrStatus(request.xhr);
                            self.transportFailure(envelope, request, failure);
                        }
                    }
                },
                onError: function(reason, exception)
                {
                    _supportsCrossDomain = false;
                    var failure = {
                        reason: reason,
                        exception: exception
                    };
                    failure.httpCode = self.xhrStatus(request.xhr);
                    if (sameStack)
                    {
                        // Keep the semantic of calling response callbacks asynchronously after the request
                        self.setTimeout(function()
                        {
                            self.transportFailure(envelope, request, failure);
                        }, 0);
                    }
                    else
                    {
                        self.transportFailure(envelope, request, failure);
                    }
                }
            });
            sameStack = false;
        }
        catch (x)
        {
            _supportsCrossDomain = false;
            // Keep the semantic of calling response callbacks asynchronously after the request
            this.setTimeout(function()
            {
                self.transportFailure(envelope, request, {
                    exception: x
                });
            }, 0);
        }
    };

    _self.reset = function()
    {
        _super.reset();
        _supportsCrossDomain = true;
    };

    return _self;
};

org.cometd.CallbackPollingTransport = function()
{
    var _super = new org.cometd.RequestTransport();
    var _self = org.cometd.Transport.derive(_super);
    var _maxLength = 2000;

    _self.accept = function(version, crossDomain, url)
    {
        return true;
    };

    _self.jsonpSend = function(packet)
    {
        throw 'Abstract';
    };

    function _failTransportFn(envelope, request, x)
    {
        var self = this;
        return function()
        {
            self.transportFailure(envelope, request, 'error', x);
        }
    }

    _self.transportSend = function(envelope, request)
    {
        var self = this;

        // Microsoft Internet Explorer has a 2083 URL max length
        // We must ensure that we stay within that length
        var start = 0;
        var length = envelope.messages.length;
        var lengths = [];
        while (length > 0)
        {
            // Encode the messages because all brackets, quotes, commas, colons, etc
            // present in the JSON will be URL encoded, taking many more characters
            var json = org.cometd.JSON.toJSON(envelope.messages.slice(start, start + length));
            var urlLength = envelope.url.length + encodeURI(json).length;

            // Let's stay on the safe side and use 2000 instead of 2083
            // also because we did not count few characters among which
            // the parameter name 'message' and the parameter 'jsonp',
            // which sum up to about 50 chars
            if (urlLength > _maxLength)
            {
                if (length === 1)
                {
                    var x = 'Bayeux message too big (' + urlLength + ' bytes, max is ' + _maxLength + ') ' +
                            'for transport ' + this.getType();
                    // Keep the semantic of calling response callbacks asynchronously after the request
                    this.setTimeout(_failTransportFn.call(this, envelope, request, x), 0);
                    return;
                }

                --length;
                continue;
            }

            lengths.push(length);
            start += length;
            length = envelope.messages.length - start;
        }

        // Here we are sure that the messages can be sent within the URL limit

        var envelopeToSend = envelope;
        if (lengths.length > 1)
        {
            var begin = 0;
            var end = lengths[0];
            this._debug('Transport', this.getType(), 'split', envelope.messages.length, 'messages into', lengths.join(' + '));
            envelopeToSend = this._mixin(false, {}, envelope);
            envelopeToSend.messages = envelope.messages.slice(begin, end);
            envelopeToSend.onSuccess = envelope.onSuccess;
            envelopeToSend.onFailure = envelope.onFailure;

            for (var i = 1; i < lengths.length; ++i)
            {
                var nextEnvelope = this._mixin(false, {}, envelope);
                begin = end;
                end += lengths[i];
                nextEnvelope.messages = envelope.messages.slice(begin, end);
                nextEnvelope.onSuccess = envelope.onSuccess;
                nextEnvelope.onFailure = envelope.onFailure;
                this.send(nextEnvelope, request.metaConnect);
            }
        }

        this._debug('Transport', this.getType(), 'sending request', request.id, 'envelope', envelopeToSend);

        try
        {
            var sameStack = true;
            this.jsonpSend({
                transport: this,
                url: envelopeToSend.url,
                sync: envelopeToSend.sync,
                headers: this.getConfiguration().requestHeaders,
                body: org.cometd.JSON.toJSON(envelopeToSend.messages),
                onSuccess: function(responses)
                {
                    var success = false;
                    try
                    {
                        var received = self.convertToMessages(responses);
                        if (received.length === 0)
                        {
                            self.transportFailure(envelopeToSend, request, {
                                httpCode: 204
                            });
                        }
                        else
                        {
                            success = true;
                            self.transportSuccess(envelopeToSend, request, received);
                        }
                    }
                    catch (x)
                    {
                        self._debug(x);
                        if (!success)
                        {
                            self.transportFailure(envelopeToSend, request, {
                                exception: x
                            });
                        }
                    }
                },
                onError: function(reason, exception)
                {
                    var failure = {
                        reason: reason,
                        exception: exception
                    };
                    if (sameStack)
                    {
                        // Keep the semantic of calling response callbacks asynchronously after the request
                        self.setTimeout(function()
                        {
                            self.transportFailure(envelopeToSend, request, failure);
                        }, 0);
                    }
                    else
                    {
                        self.transportFailure(envelopeToSend, request, failure);
                    }
                }
            });
            sameStack = false;
        }
        catch (xx)
        {
            // Keep the semantic of calling response callbacks asynchronously after the request
            this.setTimeout(function()
            {
                self.transportFailure(envelopeToSend, request, {
                    exception: xx
                });
            }, 0);
        }
    };

    return _self;
};

org.cometd.WebSocketTransport = function()
{
    var _super = new org.cometd.Transport();
    var _self = org.cometd.Transport.derive(_super);
    var _cometd;
    // By default WebSocket is supported
    var _webSocketSupported = true;
    // Whether we were able to establish a WebSocket connection
    var _webSocketConnected = false;
    var _stickyReconnect = true;
    // Envelopes that have been sent
    var _envelopes = {};
    // Timeouts for messages that have been sent
    var _timeouts = {};
    var _connecting = false;
    var _webSocket = null;
    var _connected = false;
    var _successCallback = null;

    _self.reset = function()
    {
        _super.reset();
        _webSocketSupported = true;
        _webSocketConnected = false;
        _stickyReconnect = true;
        _envelopes = {};
        _timeouts = {};
        _connecting = false;
        _webSocket = null;
        _connected = false;
        _successCallback = null;
    };

    function _websocketConnect()
    {
        // We may have multiple attempts to open a WebSocket
        // connection, for example a /meta/connect request that
        // may take time, along with a user-triggered publish.
        // Early return if we are connecting.
        if (_connecting)
        {
            return;
        }

        _connecting = true;

        // Mangle the URL, changing the scheme from 'http' to 'ws'.
        var url = _cometd.getURL().replace(/^http/, 'ws');
        this._debug('Transport', this.getType(), 'connecting to URL', url);

        try
        {
            var protocol = _cometd.getConfiguration().protocol;
            var webSocket = protocol ? new org.cometd.WebSocket(url, protocol) : new org.cometd.WebSocket(url);
        }
        catch (x)
        {
            _webSocketSupported = false;
            this._debug('Exception while creating WebSocket object', x);
            throw x;
        }

        // By default use sticky reconnects.
        _stickyReconnect = _cometd.getConfiguration().stickyReconnect !== false;

        var self = this;
        var connectTimer = null;
        var connectTimeout = _cometd.getConfiguration().connectTimeout;
        if (connectTimeout > 0)
        {
            connectTimer = this.setTimeout(function()
            {
                connectTimer = null;
                self._debug('Transport', self.getType(), 'timed out while connecting to URL', url, ':', connectTimeout, 'ms');
                // The connection was not opened, close anyway.
                var event = { code: 1000, reason: 'Connect Timeout' };
                self.webSocketClose(webSocket, event.code, event.reason);
                // Force immediate failure of pending messages to trigger reconnect.
                // This is needed because the server may not reply to our close()
                // and therefore the onclose function is never called.
                self.onClose(webSocket, event);
            }, connectTimeout);
        }

        var onopen = function()
        {
            self._debug('WebSocket opened', webSocket);
            _connecting = false;
            if (connectTimer)
            {
                self.clearTimeout(connectTimer);
                connectTimer = null;
            }

            if (_webSocket)
            {
                // We have a valid connection already, close this one.
                _cometd._warn('Closing Extra WebSocket Connections', webSocket, _webSocket);
                // Closing will eventually trigger onclose(), but
                // we do not want to clear outstanding messages.
                self.webSocketClose(webSocket, 1000, 'Extra Connection');
            }
            else
            {
                self.onOpen(webSocket);
            }
        };
        // This callback is invoked when the server sends the close frame.
        var onclose = function(event)
        {
            event = event || { code: 1000 };
            self._debug('WebSocket closing', webSocket, event);
            _connecting = false;
            if (connectTimer)
            {
                self.clearTimeout(connectTimer);
                connectTimer = null;
            }

            if (_webSocket !== null && webSocket !== _webSocket)
            {
                // We closed an extra WebSocket object that
                // we may have created during reconnection.
                self._debug('Closed Extra WebSocket Connection', webSocket);
            }
            else
            {
                self.onClose(webSocket, event);
            }
        };
        var onmessage = function(message)
        {
            self._debug('WebSocket message', message, webSocket);
            if (webSocket !== _webSocket)
            {
                _cometd._warn('Extra WebSocket Connections', webSocket, _webSocket);
            }
            self.onMessage(webSocket, message);
        };

        webSocket.onopen = onopen;
        webSocket.onclose = onclose;
        webSocket.onerror = function()
        {
            // Clients should call onclose(), but if they do not we do it here for safety.
            onclose({ code: 1002, reason: 'Error' });
        };
        webSocket.onmessage = onmessage;

        this._debug('Transport', this.getType(), 'configured callbacks on', webSocket);
    }

    function _webSocketSend(webSocket, envelope, metaConnect)
    {
        var json = org.cometd.JSON.toJSON(envelope.messages);

        webSocket.send(json);
        this._debug('Transport', this.getType(), 'sent', envelope, 'metaConnect =', metaConnect);

        // Manage the timeout waiting for the response.
        var maxDelay = this.getConfiguration().maxNetworkDelay;
        var delay = maxDelay;
        if (metaConnect)
        {
            delay += this.getAdvice().timeout;
            _connected = true;
        }

        var self = this;
        var messageIds = [];
        for (var i = 0; i < envelope.messages.length; ++i)
        {
            (function()
            {
                var message = envelope.messages[i];
                if (message.id)
                {
                    messageIds.push(message.id);
                    _timeouts[message.id] = this.setTimeout(function()
                    {
                        self._debug('Transport', self.getType(), 'timing out message', message.id, 'after', delay, 'on', webSocket);
                        var event = { code: 1000, reason: 'Message Timeout' };
                        self.webSocketClose(webSocket, event.code, event.reason);
                        // Force immediate failure of pending messages to trigger reconnect.
                        // This is needed because the server may not reply to our close()
                        // and therefore the onclose function is never called.
                        self.onClose(webSocket, event);
                    }, delay);
                }
            })();
        }

        this._debug('Transport', this.getType(), 'waiting at most', delay, 'ms for messages', messageIds, 'maxNetworkDelay', maxDelay, ', timeouts:', _timeouts);
    }

    function _send(webSocket, envelope, metaConnect)
    {
        try
        {
            if (webSocket === null)
            {
                _websocketConnect.call(this);
            }
            else
            {
                _webSocketSend.call(this, webSocket, envelope, metaConnect);
            }
        }
        catch (x)
        {
            // Keep the semantic of calling response callbacks asynchronously after the request.
            this.setTimeout(function()
            {
                envelope.onFailure(webSocket, envelope.messages, {
                    exception: x
                });
            }, 0);
        }
    }

    _self.onOpen = function(webSocket)
    {
        this._debug('Transport', this.getType(), 'opened', webSocket);
        _webSocket = webSocket;
        _webSocketConnected = true;

        this._debug('Sending pending messages', _envelopes);
        for (var key in _envelopes)
        {
            var element = _envelopes[key];
            var envelope = element[0];
            var metaConnect = element[1];
            // Store the success callback, which is independent from the envelope,
            // so that it can be used to notify arrival of messages.
            _successCallback = envelope.onSuccess;
            _webSocketSend.call(this, webSocket, envelope, metaConnect);
        }
    };

    _self.onMessage = function(webSocket, wsMessage)
    {
        this._debug('Transport', this.getType(), 'received websocket message', wsMessage, webSocket);

        var close = false;
        var messages = this.convertToMessages(wsMessage.data);
        var messageIds = [];
        for (var i = 0; i < messages.length; ++i)
        {
            var message = messages[i];

            // Detect if the message is a response to a request we made.
            // If it's a meta message, for sure it's a response; otherwise it's
            // a publish message and publish responses have the successful field.
            if (/^\/meta\//.test(message.channel) || message.successful !== undefined)
            {
                if (message.id)
                {
                    messageIds.push(message.id);

                    var timeout = _timeouts[message.id];
                    if (timeout)
                    {
                        this.clearTimeout(timeout);
                        delete _timeouts[message.id];
                        this._debug('Transport', this.getType(), 'removed timeout for message', message.id, ', timeouts', _timeouts);
                    }
                }
            }

            if ('/meta/connect' === message.channel)
            {
                _connected = false;
            }
            if ('/meta/disconnect' === message.channel && !_connected)
            {
                close = true;
            }
        }

        // Remove the envelope corresponding to the messages.
        var removed = false;
        for (var j = 0; j < messageIds.length; ++j)
        {
            var id = messageIds[j];
            for (var key in _envelopes)
            {
                var ids = key.split(',');
                var index = org.cometd.Utils.inArray(id, ids);
                if (index >= 0)
                {
                    removed = true;
                    ids.splice(index, 1);
                    var envelope = _envelopes[key][0];
                    var metaConnect = _envelopes[key][1];
                    delete _envelopes[key];
                    if (ids.length > 0)
                    {
                        _envelopes[ids.join(',')] = [envelope, metaConnect];
                    }
                    break;
                }
            }
        }
        if (removed)
        {
            this._debug('Transport', this.getType(), 'removed envelope, envelopes', _envelopes);
        }

        _successCallback.call(this, messages);

        if (close)
        {
            this.webSocketClose(webSocket, 1000, 'Disconnect');
        }
    };

    _self.onClose = function(webSocket, event)
    {
        this._debug('Transport', this.getType(), 'closed', webSocket, event);

        // Remember if we were able to connect
        // This close event could be due to server shutdown,
        // and if it restarts we want to try websocket again.
        _webSocketSupported = _stickyReconnect && _webSocketConnected;

        var timeouts = _timeouts;
        _timeouts = {};
        for (var id in timeouts)
        {
            this.clearTimeout(timeouts[id]);
        }

        var envelopes = _envelopes;
        _envelopes = {};
        for (var key in envelopes)
        {
            var envelope = envelopes[key][0];
            var metaConnect = envelopes[key][1];
            if (metaConnect)
            {
                _connected = false;
            }
            envelope.onFailure(webSocket, envelope.messages, {
                websocketCode: event.code,
                reason: event.reason
            });
        }

        _webSocket = null;
    };

    _self.registered = function(type, cometd)
    {
        _super.registered(type, cometd);
        _cometd = cometd;
    };

    _self.accept = function(version, crossDomain, url)
    {
        // Using !! to return a boolean (and not the WebSocket object).
        return _webSocketSupported && !!org.cometd.WebSocket && _cometd.websocketEnabled !== false;
    };

    _self.send = function(envelope, metaConnect)
    {
        this._debug('Transport', this.getType(), 'sending', envelope, 'metaConnect =', metaConnect);

        // Store the envelope in any case; if the websocket cannot be opened, we fail it.
        var messageIds = [];
        for (var i = 0; i < envelope.messages.length; ++i)
        {
            var message = envelope.messages[i];
            if (message.id)
            {
                messageIds.push(message.id);
            }
        }
        _envelopes[messageIds.join(',')] = [envelope, metaConnect];
        this._debug('Transport', this.getType(), 'stored envelope, envelopes', _envelopes);

        _send.call(this, _webSocket, envelope, metaConnect);
    };

    _self.webSocketClose = function(webSocket, code, reason)
    {
        try
        {
            webSocket.close(code, reason);
        }
        catch (x)
        {
            this._debug(x);
        }
    };

    _self.abort = function()
    {
        _super.abort();
        if (_webSocket)
        {
            var event = { code: 1001, reason: 'Abort' };
            this.webSocketClose(_webSocket, event.code, event.reason);
            // Force immediate failure of pending messages to trigger reconnect.
            // This is needed because the server may not reply to our close()
            // and therefore the onclose function is never called.
            this.onClose(_webSocket, event);
        }
        this.reset();
    };

    return _self;
};

/**
 * The constructor for a CometD object, identified by an optional name.
 * The default name is the string 'default'.
 * In the rare case a page needs more than one Bayeux conversation,
 * a new instance can be created via:
 * <pre>
 * var bayeuxUrl2 = ...;
 *
 * // Dojo style
 * var cometd2 = new dojox.CometD('another_optional_name');
 *
 * // jQuery style
 * var cometd2 = new $.CometD('another_optional_name');
 *
 * cometd2.init({url: bayeuxUrl2});
 * </pre>
 * @param name the optional name of this cometd object
 */
// IMPLEMENTATION NOTES:
// Be very careful in not changing the function order and pass this file every time through JSLint (http://jslint.com)
// The only implied globals must be "dojo", "org" and "window", and check that there are no "unused" warnings
// Failing to pass JSLint may result in shrinkers/minifiers to create an unusable file.
org.cometd.CometD = function(name)
{
    var _cometd = this;
    var _name = name || 'default';
    var _crossDomain = false;
    var _transports = new org.cometd.TransportRegistry();
    var _transport;
    var _status = 'disconnected';
    var _messageId = 0;
    var _clientId = null;
    var _batch = 0;
    var _messageQueue = [];
    var _internalBatch = false;
    var _listeners = {};
    var _backoff = 0;
    var _scheduledSend = null;
    var _extensions = [];
    var _advice = {};
    var _handshakeProps;
    var _handshakeCallback;
    var _callbacks = {};
    var _reestablish = false;
    var _connected = false;
    var _config = {
        protocol: null,
        stickyReconnect: true,
        connectTimeout: 0,
        maxConnections: 2,
        backoffIncrement: 1000,
        maxBackoff: 60000,
        logLevel: 'info',
        reverseIncomingExtensions: true,
        maxNetworkDelay: 10000,
        requestHeaders: {},
        appendMessageTypeToURL: true,
        autoBatch: false,
        advice: {
            timeout: 60000,
            interval: 0,
            reconnect: 'retry'
        }
    };

    function _fieldValue(object, name)
    {
        try
        {
            return object[name];
        }
        catch (x)
        {
            return undefined;
        }
    }

    /**
     * Mixes in the given objects into the target object by copying the properties.
     * @param deep if the copy must be deep
     * @param target the target object
     * @param objects the objects whose properties are copied into the target
     */
    this._mixin = function(deep, target, objects)
    {
        var result = target || {};

        // Skip first 2 parameters (deep and target), and loop over the others
        for (var i = 2; i < arguments.length; ++i)
        {
            var object = arguments[i];

            if (object === undefined || object === null)
            {
                continue;
            }

            for (var propName in object)
            {
                var prop = _fieldValue(object, propName);
                var targ = _fieldValue(result, propName);

                // Avoid infinite loops
                if (prop === target)
                {
                    continue;
                }
                // Do not mixin undefined values
                if (prop === undefined)
                {
                    continue;
                }

                if (deep && typeof prop === 'object' && prop !== null)
                {
                    if (prop instanceof Array)
                    {
                        result[propName] = this._mixin(deep, targ instanceof Array ? targ : [], prop);
                    }
                    else
                    {
                        var source = typeof targ === 'object' && !(targ instanceof Array) ? targ : {};
                        result[propName] = this._mixin(deep, source, prop);
                    }
                }
                else
                {
                    result[propName] = prop;
                }
            }
        }

        return result;
    };

    function _isString(value)
    {
        return org.cometd.Utils.isString(value);
    }

    function _isFunction(value)
    {
        if (value === undefined || value === null)
        {
            return false;
        }
        return typeof value === 'function';
    }

    function _log(level, args)
    {
        if (window.console)
        {
            var logger = window.console[level];
            if (_isFunction(logger))
            {
                logger.apply(window.console, args);
            }
        }
    }

    this._warn = function()
    {
        _log('warn', arguments);
    };

    this._info = function()
    {
        if (_config.logLevel !== 'warn')
        {
            _log('info', arguments);
        }
    };

    this._debug = function()
    {
        if (_config.logLevel === 'debug')
        {
            _log('debug', arguments);
        }
    };

    /**
     * Returns whether the given hostAndPort is cross domain.
     * The default implementation checks against window.location.host
     * but this function can be overridden to make it work in non-browser
     * environments.
     *
     * @param hostAndPort the host and port in format host:port
     * @return whether the given hostAndPort is cross domain
     */
    this._isCrossDomain = function(hostAndPort)
    {
        return hostAndPort && hostAndPort !== window.location.host;
    };

    function _configure(configuration)
    {
        _cometd._debug('Configuring cometd object with', configuration);
        // Support old style param, where only the Bayeux server URL was passed
        if (_isString(configuration))
        {
            configuration = { url: configuration };
        }
        if (!configuration)
        {
            configuration = {};
        }

        _config = _cometd._mixin(false, _config, configuration);

        var url = _cometd.getURL();
        if (!url)
        {
            throw 'Missing required configuration parameter \'url\' specifying the Bayeux server URL';
        }

        // Check if we're cross domain
        // [1] = protocol://, [2] = host:port, [3] = host, [4] = IPv6_host, [5] = IPv4_host, [6] = :port, [7] = port, [8] = uri, [9] = rest
        var urlParts = /(^https?:\/\/)?(((\[[^\]]+\])|([^:\/\?#]+))(:(\d+))?)?([^\?#]*)(.*)?/.exec(url);
        var hostAndPort = urlParts[2];
        var uri = urlParts[8];
        var afterURI = urlParts[9];
        _crossDomain = _cometd._isCrossDomain(hostAndPort);

        // Check if appending extra path is supported
        if (_config.appendMessageTypeToURL)
        {
            if (afterURI !== undefined && afterURI.length > 0)
            {
                _cometd._info('Appending message type to URI ' + uri + afterURI + ' is not supported, disabling \'appendMessageTypeToURL\' configuration');
                _config.appendMessageTypeToURL = false;
            }
            else
            {
                var uriSegments = uri.split('/');
                var lastSegmentIndex = uriSegments.length - 1;
                if (uri.match(/\/$/))
                {
                    lastSegmentIndex -= 1;
                }
                if (uriSegments[lastSegmentIndex].indexOf('.') >= 0)
                {
                    // Very likely the CometD servlet's URL pattern is mapped to an extension, such as *.cometd
                    // It will be difficult to add the extra path in this case
                    _cometd._info('Appending message type to URI ' + uri + ' is not supported, disabling \'appendMessageTypeToURL\' configuration');
                    _config.appendMessageTypeToURL = false;
                }
            }
        }
    }

    function _removeListener(subscription)
    {
        if (subscription)
        {
            var subscriptions = _listeners[subscription.channel];
            if (subscriptions && subscriptions[subscription.id])
            {
                delete subscriptions[subscription.id];
                _cometd._debug('Removed', subscription.listener ? 'listener' : 'subscription', subscription);
            }
        }
    }

    function _removeSubscription(subscription)
    {
        if (subscription && !subscription.listener)
        {
            _removeListener(subscription);
        }
    }

    function _clearSubscriptions()
    {
        for (var channel in _listeners)
        {
            var subscriptions = _listeners[channel];
            if (subscriptions)
            {
                for (var i = 0; i < subscriptions.length; ++i)
                {
                    _removeSubscription(subscriptions[i]);
                }
            }
        }
    }

    function _setStatus(newStatus)
    {
        if (_status !== newStatus)
        {
            _cometd._debug('Status', _status, '->', newStatus);
            _status = newStatus;
        }
    }

    function _isDisconnected()
    {
        return _status === 'disconnecting' || _status === 'disconnected';
    }

    function _nextMessageId()
    {
        return ++_messageId;
    }

    function _applyExtension(scope, callback, name, message, outgoing)
    {
        try
        {
            return callback.call(scope, message);
        }
        catch (x)
        {
            _cometd._debug('Exception during execution of extension', name, x);
            var exceptionCallback = _cometd.onExtensionException;
            if (_isFunction(exceptionCallback))
            {
                _cometd._debug('Invoking extension exception callback', name, x);
                try
                {
                    exceptionCallback.call(_cometd, x, name, outgoing, message);
                }
                catch(xx)
                {
                    _cometd._info('Exception during execution of exception callback in extension', name, xx);
                }
            }
            return message;
        }
    }

    function _applyIncomingExtensions(message)
    {
        for (var i = 0; i < _extensions.length; ++i)
        {
            if (message === undefined || message === null)
            {
                break;
            }

            var index = _config.reverseIncomingExtensions ? _extensions.length - 1 - i : i;
            var extension = _extensions[index];
            var callback = extension.extension.incoming;
            if (_isFunction(callback))
            {
                var result = _applyExtension(extension.extension, callback, extension.name, message, false);
                message = result === undefined ? message : result;
            }
        }
        return message;
    }

    function _applyOutgoingExtensions(message)
    {
        for (var i = 0; i < _extensions.length; ++i)
        {
            if (message === undefined || message === null)
            {
                break;
            }

            var extension = _extensions[i];
            var callback = extension.extension.outgoing;
            if (_isFunction(callback))
            {
                var result = _applyExtension(extension.extension, callback, extension.name, message, true);
                message = result === undefined ? message : result;
            }
        }
        return message;
    }

    function _notify(channel, message)
    {
        var subscriptions = _listeners[channel];
        if (subscriptions && subscriptions.length > 0)
        {
            for (var i = 0; i < subscriptions.length; ++i)
            {
                var subscription = subscriptions[i];
                // Subscriptions may come and go, so the array may have 'holes'
                if (subscription)
                {
                    try
                    {
                        subscription.callback.call(subscription.scope, message);
                    }
                    catch (x)
                    {
                        _cometd._debug('Exception during notification', subscription, message, x);
                        var listenerCallback = _cometd.onListenerException;
                        if (_isFunction(listenerCallback))
                        {
                            _cometd._debug('Invoking listener exception callback', subscription, x);
                            try
                            {
                                listenerCallback.call(_cometd, x, subscription, subscription.listener, message);
                            }
                            catch (xx)
                            {
                                _cometd._info('Exception during execution of listener callback', subscription, xx);
                            }
                        }
                    }
                }
            }
        }
    }

    function _notifyListeners(channel, message)
    {
        // Notify direct listeners
        _notify(channel, message);

        // Notify the globbing listeners
        var channelParts = channel.split('/');
        var last = channelParts.length - 1;
        for (var i = last; i > 0; --i)
        {
            var channelPart = channelParts.slice(0, i).join('/') + '/*';
            // We don't want to notify /foo/* if the channel is /foo/bar/baz,
            // so we stop at the first non recursive globbing
            if (i === last)
            {
                _notify(channelPart, message);
            }
            // Add the recursive globber and notify
            channelPart += '*';
            _notify(channelPart, message);
        }
    }

    function _cancelDelayedSend()
    {
        if (_scheduledSend !== null)
        {
            org.cometd.Utils.clearTimeout(_scheduledSend);
        }
        _scheduledSend = null;
    }

    function _delayedSend(operation)
    {
        _cancelDelayedSend();
        var delay = _advice.interval + _backoff;
        _cometd._debug('Function scheduled in', delay, 'ms, interval =', _advice.interval, 'backoff =', _backoff, operation);
        _scheduledSend = org.cometd.Utils.setTimeout(_cometd, operation, delay);
    }

    // Needed to break cyclic dependencies between function definitions
    var _handleMessages;
    var _handleFailure;

    /**
     * Delivers the messages to the CometD server
     * @param sync whether the send is synchronous
     * @param messages the array of messages to send
     * @param longpoll true if this send is a long poll
     * @param extraPath an extra path to append to the Bayeux server URL
     */
    function _send(sync, messages, longpoll, extraPath)
    {
        // We must be sure that the messages have a clientId.
        // This is not guaranteed since the handshake may take time to return
        // (and hence the clientId is not known yet) and the application
        // may create other messages.
        for (var i = 0; i < messages.length; ++i)
        {
            var message = messages[i];
            var messageId = '' + _nextMessageId();
            message.id = messageId;

            if (_clientId)
            {
                message.clientId = _clientId;
            }

            var callback = undefined;
            if (_isFunction(message._callback))
            {
                callback = message._callback;
                // Remove the callback before calling the extensions
                delete message._callback;
            }

            message = _applyOutgoingExtensions(message);
            if (message !== undefined && message !== null)
            {
                // Extensions may have modified the message id, but we need to own it.
                message.id = messageId;
                messages[i] = message;
                if (callback)
                {
                    _callbacks[messageId] = callback;
                }
            }
            else
            {
                messages.splice(i--, 1);
            }
        }

        if (messages.length === 0)
        {
            return;
        }

        var url = _cometd.getURL();
        if (_config.appendMessageTypeToURL)
        {
            // If url does not end with '/', then append it
            if (!url.match(/\/$/))
            {
                url = url + '/';
            }
            if (extraPath)
            {
                url = url + extraPath;
            }
        }

        var envelope = {
            url: url,
            sync: sync,
            messages: messages,
            onSuccess: function(rcvdMessages)
            {
                try
                {
                    _handleMessages.call(_cometd, rcvdMessages);
                }
                catch (x)
                {
                    _cometd._debug('Exception during handling of messages', x);
                }
            },
            onFailure: function(conduit, messages, failure)
            {
                try
                {
                    var transport = _cometd.getTransport();
                    failure.connectionType = transport ? transport.getType() : "unknown";
                    _handleFailure.call(_cometd, conduit, messages, failure);
                }
                catch (x)
                {
                    _cometd._debug('Exception during handling of failure', x);
                }
            }
        };
        _cometd._debug('Send', envelope);
        _transport.send(envelope, longpoll);
    }

    function _queueSend(message)
    {
        if (_batch > 0 || _internalBatch === true)
        {
            _messageQueue.push(message);
        }
        else
        {
            _send(false, [message], false);
        }
    }

    /**
     * Sends a complete bayeux message.
     * This method is exposed as a public so that extensions may use it
     * to send bayeux message directly, for example in case of re-sending
     * messages that have already been sent but that for some reason must
     * be resent.
     */
    this.send = _queueSend;

    function _resetBackoff()
    {
        _backoff = 0;
    }

    function _increaseBackoff()
    {
        if (_backoff < _config.maxBackoff)
        {
            _backoff += _config.backoffIncrement;
        }
    }

    /**
     * Starts a the batch of messages to be sent in a single request.
     * @see #_endBatch(sendMessages)
     */
    function _startBatch()
    {
        ++_batch;
    }

    function _flushBatch()
    {
        var messages = _messageQueue;
        _messageQueue = [];
        if (messages.length > 0)
        {
            _send(false, messages, false);
        }
    }

    /**
     * Ends the batch of messages to be sent in a single request,
     * optionally sending messages present in the message queue depending
     * on the given argument.
     * @see #_startBatch()
     */
    function _endBatch()
    {
        --_batch;
        if (_batch < 0)
        {
            throw 'Calls to startBatch() and endBatch() are not paired';
        }

        if (_batch === 0 && !_isDisconnected() && !_internalBatch)
        {
            _flushBatch();
        }
    }

    /**
     * Sends the connect message
     */
    function _connect()
    {
        if (!_isDisconnected())
        {
            var message = {
                channel: '/meta/connect',
                connectionType: _transport.getType()
            };

            // In case of reload or temporary loss of connection
            // we want the next successful connect to return immediately
            // instead of being held by the server, so that connect listeners
            // can be notified that the connection has been re-established
            if (!_connected)
            {
                message.advice = { timeout: 0 };
            }

            _setStatus('connecting');
            _cometd._debug('Connect sent', message);
            _send(false, [message], true, 'connect');
            _setStatus('connected');
        }
    }

    function _delayedConnect()
    {
        _setStatus('connecting');
        _delayedSend(function()
        {
            _connect();
        });
    }

    function _updateAdvice(newAdvice)
    {
        if (newAdvice)
        {
            _advice = _cometd._mixin(false, {}, _config.advice, newAdvice);
            _cometd._debug('New advice', _advice);
        }
    }

    function _disconnect(abort)
    {
        _cancelDelayedSend();
        if (abort && _transport)
        {
            _transport.abort();
        }
        _clientId = null;
        _setStatus('disconnected');
        _batch = 0;
        _resetBackoff();
        _transport = null;

        // Fail any existing queued message
        if (_messageQueue.length > 0)
        {
            var messages = _messageQueue;
            _messageQueue = [];
            _handleFailure.call(_cometd, undefined, messages, {
                reason: 'Disconnected'
            });
        }
    }

    function _notifyTransportFailure(oldTransport, newTransport, failure)
    {
        var callback = _cometd.onTransportFailure;
        if (_isFunction(callback))
        {
            _cometd._debug('Invoking transport failure callback', oldTransport, newTransport, failure);
            try
            {
                callback.call(_cometd, oldTransport, newTransport, failure);
            }
            catch (x)
            {
                _cometd._info('Exception during execution of transport failure callback', x);

            }
        }
    }

    /**
     * Sends the initial handshake message
     */
    function _handshake(handshakeProps, handshakeCallback)
    {
        if (_isFunction(handshakeProps))
        {
            handshakeCallback = handshakeProps;
            handshakeProps = undefined;
        }

        _clientId = null;

        _clearSubscriptions();

        // Reset the transports if we're not retrying the handshake
        if (_isDisconnected())
        {
            _transports.reset();
            _updateAdvice(_config.advice);
        }
        else
        {
            // We are retrying the handshake, either because another handshake failed
            // and we're backing off, or because the server timed us out and asks us to
            // re-handshake: in both cases, make sure that if the handshake succeeds
            // the next action is a connect.
            _updateAdvice(_cometd._mixin(false, _advice, {reconnect: 'retry'}));
        }

        _batch = 0;

        // Mark the start of an internal batch.
        // This is needed because handshake and connect are async.
        // It may happen that the application calls init() then subscribe()
        // and the subscribe message is sent before the connect message, if
        // the subscribe message is not held until the connect message is sent.
        // So here we start a batch to hold temporarily any message until
        // the connection is fully established.
        _internalBatch = true;

        // Save the properties provided by the user, so that
        // we can reuse them during automatic re-handshake
        _handshakeProps = handshakeProps;
        _handshakeCallback = handshakeCallback;

        var version = '1.0';

        // Figure out the transports to send to the server
        var url = _cometd.getURL();
        var transportTypes = _transports.findTransportTypes(version, _crossDomain, url);

        var bayeuxMessage = {
            version: version,
            minimumVersion: version,
            channel: '/meta/handshake',
            supportedConnectionTypes: transportTypes,
            _callback: handshakeCallback,
            advice: {
                timeout: _advice.timeout,
                interval: _advice.interval
            }
        };
        // Do not allow the user to mess with the required properties,
        // so merge first the user properties and *then* the bayeux message
        var message = _cometd._mixin(false, {}, _handshakeProps, bayeuxMessage);

        // Pick up the first available transport as initial transport
        // since we don't know if the server supports it
        if (!_transport)
        {
            _transport = _transports.negotiateTransport(transportTypes, version, _crossDomain, url);
            if (!_transport)
            {
                var failure = 'Could not find initial transport among: ' + _transports.getTransportTypes();
                _cometd._warn(failure);
                throw failure;
            }
        }

        _cometd._debug('Initial transport is', _transport.getType());

        // We started a batch to hold the application messages,
        // so here we must bypass it and send immediately.
        _setStatus('handshaking');
        _cometd._debug('Handshake sent', message);
        _send(false, [message], false, 'handshake');
    }

    function _delayedHandshake()
    {
        _setStatus('handshaking');

        // We will call _handshake() which will reset _clientId, but we want to avoid
        // that between the end of this method and the call to _handshake() someone may
        // call publish() (or other methods that call _queueSend()).
        _internalBatch = true;

        _delayedSend(function()
        {
            _handshake(_handshakeProps, _handshakeCallback);
        });
    }

    function _handleCallback(message)
    {
        var callback = _callbacks[message.id];
        if (_isFunction(callback))
        {
            delete _callbacks[message.id];
            callback.call(_cometd, message);
        }
    }

    function _failHandshake(message)
    {
        _handleCallback(message);
        _notifyListeners('/meta/handshake', message);
        _notifyListeners('/meta/unsuccessful', message);

        // Only try again if we haven't been disconnected and
        // the advice permits us to retry the handshake
        var retry = !_isDisconnected() && _advice.reconnect !== 'none';
        if (retry)
        {
            _increaseBackoff();
            _delayedHandshake();
        }
        else
        {
            _disconnect(false);
        }
    }

    function _handshakeResponse(message)
    {
        if (message.successful)
        {
            // Save clientId, figure out transport, then follow the advice to connect
            _clientId = message.clientId;

            var url = _cometd.getURL();
            var newTransport = _transports.negotiateTransport(message.supportedConnectionTypes, message.version, _crossDomain, url);
            if (newTransport === null)
            {
                var failure = 'Could not negotiate transport with server; client=[' +
                    _transports.findTransportTypes(message.version, _crossDomain, url) +
                    '], server=[' + message.supportedConnectionTypes + ']';
                var oldTransport = _cometd.getTransport();
                _notifyTransportFailure(oldTransport.getType(), null, {
                    reason: failure,
                    connectionType: oldTransport.getType(),
                    transport: oldTransport
                });
                _cometd._warn(failure);
                _disconnect(true);
                return;
            }
            else if (_transport !== newTransport)
            {
                _cometd._debug('Transport', _transport.getType(), '->', newTransport.getType());
                _transport = newTransport;
            }

            // End the internal batch and allow held messages from the application
            // to go to the server (see _handshake() where we start the internal batch).
            _internalBatch = false;
            _flushBatch();

            // Here the new transport is in place, as well as the clientId, so
            // the listeners can perform a publish() if they want.
            // Notify the listeners before the connect below.
            message.reestablish = _reestablish;
            _reestablish = true;

            _handleCallback(message);
            _notifyListeners('/meta/handshake', message);

            var action = _isDisconnected() ? 'none' : _advice.reconnect;
            switch (action)
            {
                case 'retry':
                    _resetBackoff();
                    _delayedConnect();
                    break;
                case 'none':
                    _disconnect(false);
                    break;
                default:
                    throw 'Unrecognized advice action ' + action;
            }
        }
        else
        {
            _failHandshake(message);
        }
    }

    function _handshakeFailure(message)
    {
        var version = '1.0';
        var url = _cometd.getURL();
        var oldTransport = _cometd.getTransport();
        var transportTypes = _transports.findTransportTypes(version, _crossDomain, url);
        var newTransport = _transports.negotiateTransport(transportTypes, version, _crossDomain, url);
        if (!newTransport)
        {
            _notifyTransportFailure(oldTransport.getType(), null, message.failure);
            _cometd._warn('Could not negotiate transport; client=[' + transportTypes + ']');
            _disconnect(true);
            _failHandshake(message);
        }
        else
        {
            _cometd._debug('Transport', oldTransport.getType(), '->', newTransport.getType());
            _notifyTransportFailure(oldTransport.getType(), newTransport.getType(), message.failure);
            _failHandshake(message);
            _transport = newTransport;
        }
    }

    function _failConnect(message)
    {
        // Notify the listeners after the status change but before the next action
        _notifyListeners('/meta/connect', message);
        _notifyListeners('/meta/unsuccessful', message);

        // This may happen when the server crashed, the current clientId
        // will be invalid, and the server will ask to handshake again
        // Listeners can call disconnect(), so check the state after they run
        var action = _isDisconnected() ? 'none' : _advice.reconnect;
        switch (action)
        {
            case 'retry':
                _delayedConnect();
                _increaseBackoff();
                break;
            case 'handshake':
                // The current transport may be failed (e.g. network disconnection)
                // Reset the transports so the new handshake picks up the right one
                _transports.reset();
                _resetBackoff();
                _delayedHandshake();
                break;
            case 'none':
                _disconnect(false);
                break;
            default:
                throw 'Unrecognized advice action' + action;
        }
    }

    function _connectResponse(message)
    {
        _connected = message.successful;

        if (_connected)
        {
            _notifyListeners('/meta/connect', message);

            // Normally, the advice will say "reconnect: 'retry', interval: 0"
            // and the server will hold the request, so when a response returns
            // we immediately call the server again (long polling)
            // Listeners can call disconnect(), so check the state after they run
            var action = _isDisconnected() ? 'none' : _advice.reconnect;
            switch (action)
            {
                case 'retry':
                    _resetBackoff();
                    _delayedConnect();
                    break;
                case 'none':
                    _disconnect(false);
                    break;
                default:
                    throw 'Unrecognized advice action ' + action;
            }
        }
        else
        {
            _failConnect(message);
        }
    }

    function _connectFailure(message)
    {
        _connected = false;
        _failConnect(message);
    }

    function _failDisconnect(message)
    {
        _disconnect(true);
        _handleCallback(message);
        _notifyListeners('/meta/disconnect', message);
        _notifyListeners('/meta/unsuccessful', message);
    }

    function _disconnectResponse(message)
    {
        if (message.successful)
        {
            _disconnect(false);
            _handleCallback(message);
            _notifyListeners('/meta/disconnect', message);
        }
        else
        {
            _failDisconnect(message);
        }
    }

    function _disconnectFailure(message)
    {
        _failDisconnect(message);
    }

    function _failSubscribe(message)
    {
        var subscriptions = _listeners[message.subscription];
        if (subscriptions)
        {
            for (var i = subscriptions.length - 1; i >= 0; --i)
            {
                var subscription = subscriptions[i];
                if (subscription && !subscription.listener)
                {
                    delete subscriptions[i];
                    _cometd._debug('Removed failed subscription', subscription);
                    break;
                }
            }
        }
        _handleCallback(message);
        _notifyListeners('/meta/subscribe', message);
        _notifyListeners('/meta/unsuccessful', message);
    }

    function _subscribeResponse(message)
    {
        if (message.successful)
        {
            _handleCallback(message);
            _notifyListeners('/meta/subscribe', message);
        }
        else
        {
            _failSubscribe(message);
        }
    }

    function _subscribeFailure(message)
    {
        _failSubscribe(message);
    }

    function _failUnsubscribe(message)
    {
        _handleCallback(message);
        _notifyListeners('/meta/unsubscribe', message);
        _notifyListeners('/meta/unsuccessful', message);
    }

    function _unsubscribeResponse(message)
    {
        if (message.successful)
        {
            _handleCallback(message);
            _notifyListeners('/meta/unsubscribe', message);
        }
        else
        {
            _failUnsubscribe(message);
        }
    }

    function _unsubscribeFailure(message)
    {
        _failUnsubscribe(message);
    }

    function _failMessage(message)
    {
        _handleCallback(message);
        _notifyListeners('/meta/publish', message);
        _notifyListeners('/meta/unsuccessful', message);
    }

    function _messageResponse(message)
    {
        if (message.successful === undefined)
        {
            if (message.data !== undefined)
            {
                // It is a plain message, and not a bayeux meta message
                _notifyListeners(message.channel, message);
            }
            else
            {
                _cometd._warn('Unknown Bayeux Message', message);
            }
        }
        else
        {
            if (message.successful)
            {
                _handleCallback(message);
                _notifyListeners('/meta/publish', message);
            }
            else
            {
                _failMessage(message);
            }
        }
    }

    function _messageFailure(failure)
    {
        _failMessage(failure);
    }

    function _receive(message)
    {
        message = _applyIncomingExtensions(message);
        if (message === undefined || message === null)
        {
            return;
        }

        _updateAdvice(message.advice);

        var channel = message.channel;
        switch (channel)
        {
            case '/meta/handshake':
                _handshakeResponse(message);
                break;
            case '/meta/connect':
                _connectResponse(message);
                break;
            case '/meta/disconnect':
                _disconnectResponse(message);
                break;
            case '/meta/subscribe':
                _subscribeResponse(message);
                break;
            case '/meta/unsubscribe':
                _unsubscribeResponse(message);
                break;
            default:
                _messageResponse(message);
                break;
        }
    }

    /**
     * Receives a message.
     * This method is exposed as a public so that extensions may inject
     * messages simulating that they had been received.
     */
    this.receive = _receive;

    _handleMessages = function(rcvdMessages)
    {
        _cometd._debug('Received', rcvdMessages);

        for (var i = 0; i < rcvdMessages.length; ++i)
        {
            var message = rcvdMessages[i];
            _receive(message);
        }
    };

    _handleFailure = function(conduit, messages, failure)
    {
        _cometd._debug('handleFailure', conduit, messages, failure);

        failure.transport = conduit;
        for (var i = 0; i < messages.length; ++i)
        {
            var message = messages[i];
            var failureMessage = {
                id: message.id,
                successful: false,
                channel: message.channel,
                failure: failure
            };
            failure.message = message;
            switch (message.channel)
            {
                case '/meta/handshake':
                    _handshakeFailure(failureMessage);
                    break;
                case '/meta/connect':
                    _connectFailure(failureMessage);
                    break;
                case '/meta/disconnect':
                    _disconnectFailure(failureMessage);
                    break;
                case '/meta/subscribe':
                    failureMessage.subscription = message.subscription;
                    _subscribeFailure(failureMessage);
                    break;
                case '/meta/unsubscribe':
                    failureMessage.subscription = message.subscription;
                    _unsubscribeFailure(failureMessage);
                    break;
                default:
                    _messageFailure(failureMessage);
                    break;
            }
        }
    };

    function _hasSubscriptions(channel)
    {
        var subscriptions = _listeners[channel];
        if (subscriptions)
        {
            for (var i = 0; i < subscriptions.length; ++i)
            {
                if (subscriptions[i])
                {
                    return true;
                }
            }
        }
        return false;
    }

    function _resolveScopedCallback(scope, callback)
    {
        var delegate = {
            scope: scope,
            method: callback
        };
        if (_isFunction(scope))
        {
            delegate.scope = undefined;
            delegate.method = scope;
        }
        else
        {
            if (_isString(callback))
            {
                if (!scope)
                {
                    throw 'Invalid scope ' + scope;
                }
                delegate.method = scope[callback];
                if (!_isFunction(delegate.method))
                {
                    throw 'Invalid callback ' + callback + ' for scope ' + scope;
                }
            }
            else if (!_isFunction(callback))
            {
                throw 'Invalid callback ' + callback;
            }
        }
        return delegate;
    }

    function _addListener(channel, scope, callback, isListener)
    {
        // The data structure is a map<channel, subscription[]>, where each subscription
        // holds the callback to be called and its scope.

        var delegate = _resolveScopedCallback(scope, callback);
        _cometd._debug('Adding', isListener ? 'listener' : 'subscription', 'on', channel, 'with scope', delegate.scope, 'and callback', delegate.method);

        var subscription = {
            channel: channel,
            scope: delegate.scope,
            callback: delegate.method,
            listener: isListener
        };

        var subscriptions = _listeners[channel];
        if (!subscriptions)
        {
            subscriptions = [];
            _listeners[channel] = subscriptions;
        }

        // Pushing onto an array appends at the end and returns the id associated with the element increased by 1.
        // Note that if:
        // a.push('a'); var hb=a.push('b'); delete a[hb-1]; var hc=a.push('c');
        // then:
        // hc==3, a.join()=='a',,'c', a.length==3
        subscription.id = subscriptions.push(subscription) - 1;

        _cometd._debug('Added', isListener ? 'listener' : 'subscription', subscription);

        // For backward compatibility: we used to return [channel, subscription.id]
        subscription[0] = channel;
        subscription[1] = subscription.id;

        return subscription;
    }

    //
    // PUBLIC API
    //

    /**
     * Registers the given transport under the given transport type.
     * The optional index parameter specifies the "priority" at which the
     * transport is registered (where 0 is the max priority).
     * If a transport with the same type is already registered, this function
     * does nothing and returns false.
     * @param type the transport type
     * @param transport the transport object
     * @param index the index at which this transport is to be registered
     * @return true if the transport has been registered, false otherwise
     * @see #unregisterTransport(type)
     */
    this.registerTransport = function(type, transport, index)
    {
        var result = _transports.add(type, transport, index);
        if (result)
        {
            this._debug('Registered transport', type);

            if (_isFunction(transport.registered))
            {
                transport.registered(type, this);
            }
        }
        return result;
    };

    /**
     * @return an array of all registered transport types
     */
    this.getTransportTypes = function()
    {
        return _transports.getTransportTypes();
    };

    /**
     * Unregisters the transport with the given transport type.
     * @param type the transport type to unregister
     * @return the transport that has been unregistered,
     * or null if no transport was previously registered under the given transport type
     */
    this.unregisterTransport = function(type)
    {
        var transport = _transports.remove(type);
        if (transport !== null)
        {
            this._debug('Unregistered transport', type);

            if (_isFunction(transport.unregistered))
            {
                transport.unregistered();
            }
        }
        return transport;
    };

    this.unregisterTransports = function()
    {
        _transports.clear();
    };

    this.findTransport = function(name)
    {
        return _transports.find(name);
    };

    /**
     * Configures the initial Bayeux communication with the Bayeux server.
     * Configuration is passed via an object that must contain a mandatory field <code>url</code>
     * of type string containing the URL of the Bayeux server.
     * @param configuration the configuration object
     */
    this.configure = function(configuration)
    {
        _configure.call(this, configuration);
    };

    /**
     * Configures and establishes the Bayeux communication with the Bayeux server
     * via a handshake and a subsequent connect.
     * @param configuration the configuration object
     * @param handshakeProps an object to be merged with the handshake message
     * @see #configure(configuration)
     * @see #handshake(handshakeProps)
     */
    this.init = function(configuration, handshakeProps)
    {
        this.configure(configuration);
        this.handshake(handshakeProps);
    };

    /**
     * Establishes the Bayeux communication with the Bayeux server
     * via a handshake and a subsequent connect.
     * @param handshakeProps an object to be merged with the handshake message
     * @param handshakeCallback a function to be invoked when the handshake is acknowledged
     */
    this.handshake = function(handshakeProps, handshakeCallback)
    {
        _setStatus('disconnected');
        _reestablish = false;
        _handshake(handshakeProps, handshakeCallback);
    };

    /**
     * Disconnects from the Bayeux server.
     * It is possible to suggest to attempt a synchronous disconnect, but this feature
     * may only be available in certain transports (for example, long-polling may support
     * it, callback-polling certainly does not).
     * @param sync whether attempt to perform a synchronous disconnect
     * @param disconnectProps an object to be merged with the disconnect message
     * @param disconnectCallback a function to be invoked when the disconnect is acknowledged
     */
    this.disconnect = function(sync, disconnectProps, disconnectCallback)
    {
        if (_isDisconnected())
        {
            return;
        }

        if (typeof sync !== 'boolean')
        {
            disconnectCallback = disconnectProps;
            disconnectProps = sync;
            sync = false;
        }
        if (_isFunction(disconnectProps))
        {
            disconnectCallback = disconnectProps;
            disconnectProps = undefined;
        }

        var bayeuxMessage = {
            channel: '/meta/disconnect',
            _callback: disconnectCallback
        };
        var message = this._mixin(false, {}, disconnectProps, bayeuxMessage);
        _setStatus('disconnecting');
        _send(sync === true, [message], false, 'disconnect');
    };

    /**
     * Marks the start of a batch of application messages to be sent to the server
     * in a single request, obtaining a single response containing (possibly) many
     * application reply messages.
     * Messages are held in a queue and not sent until {@link #endBatch()} is called.
     * If startBatch() is called multiple times, then an equal number of endBatch()
     * calls must be made to close and send the batch of messages.
     * @see #endBatch()
     */
    this.startBatch = function()
    {
        _startBatch();
    };

    /**
     * Marks the end of a batch of application messages to be sent to the server
     * in a single request.
     * @see #startBatch()
     */
    this.endBatch = function()
    {
        _endBatch();
    };

    /**
     * Executes the given callback in the given scope, surrounded by a {@link #startBatch()}
     * and {@link #endBatch()} calls.
     * @param scope the scope of the callback, may be omitted
     * @param callback the callback to be executed within {@link #startBatch()} and {@link #endBatch()} calls
     */
    this.batch = function(scope, callback)
    {
        var delegate = _resolveScopedCallback(scope, callback);
        this.startBatch();
        try
        {
            delegate.method.call(delegate.scope);
            this.endBatch();
        }
        catch (x)
        {
            this._info('Exception during execution of batch', x);
            this.endBatch();
            throw x;
        }
    };

    /**
     * Adds a listener for bayeux messages, performing the given callback in the given scope
     * when a message for the given channel arrives.
     * @param channel the channel the listener is interested to
     * @param scope the scope of the callback, may be omitted
     * @param callback the callback to call when a message is sent to the channel
     * @returns the subscription handle to be passed to {@link #removeListener(object)}
     * @see #removeListener(subscription)
     */
    this.addListener = function(channel, scope, callback)
    {
        if (arguments.length < 2)
        {
            throw 'Illegal arguments number: required 2, got ' + arguments.length;
        }
        if (!_isString(channel))
        {
            throw 'Illegal argument type: channel must be a string';
        }

        return _addListener(channel, scope, callback, true);
    };

    /**
     * Removes the subscription obtained with a call to {@link #addListener(string, object, function)}.
     * @param subscription the subscription to unsubscribe.
     * @see #addListener(channel, scope, callback)
     */
    this.removeListener = function(subscription)
    {
        // Beware of subscription.id == 0, which is falsy => cannot use !subscription.id
        if (!subscription || !subscription.channel || !("id" in subscription))
        {
            throw 'Invalid argument: expected subscription, not ' + subscription;
        }

        _removeListener(subscription);
    };

    /**
     * Removes all listeners registered with {@link #addListener(channel, scope, callback)} or
     * {@link #subscribe(channel, scope, callback)}.
     */
    this.clearListeners = function()
    {
        _listeners = {};
    };

    /**
     * Subscribes to the given channel, performing the given callback in the given scope
     * when a message for the channel arrives.
     * @param channel the channel to subscribe to
     * @param scope the scope of the callback, may be omitted
     * @param callback the callback to call when a message is sent to the channel
     * @param subscribeProps an object to be merged with the subscribe message
     * @param subscribeCallback a function to be invoked when the subscription is acknowledged
     * @return the subscription handle to be passed to {@link #unsubscribe(object)}
     */
    this.subscribe = function(channel, scope, callback, subscribeProps, subscribeCallback)
    {
        if (arguments.length < 2)
        {
            throw 'Illegal arguments number: required 2, got ' + arguments.length;
        }
        if (!_isString(channel))
        {
            throw 'Illegal argument type: channel must be a string';
        }
        if (_isDisconnected())
        {
            throw 'Illegal state: already disconnected';
        }

        // Normalize arguments
        if (_isFunction(scope))
        {
            subscribeCallback = subscribeProps;
            subscribeProps = callback;
            callback = scope;
            scope = undefined;
        }
        if (_isFunction(subscribeProps))
        {
            subscribeCallback = subscribeProps;
            subscribeProps = undefined;
        }

        // Only send the message to the server if this client has not yet subscribed to the channel
        var send = !_hasSubscriptions(channel);

        var subscription = _addListener(channel, scope, callback, false);

        if (send)
        {
            // Send the subscription message after the subscription registration to avoid
            // races where the server would send a message to the subscribers, but here
            // on the client the subscription has not been added yet to the data structures
            var bayeuxMessage = {
                channel: '/meta/subscribe',
                subscription: channel,
                _callback: subscribeCallback
            };
            var message = this._mixin(false, {}, subscribeProps, bayeuxMessage);
            _queueSend(message);
        }

        return subscription;
    };

    /**
     * Unsubscribes the subscription obtained with a call to {@link #subscribe(string, object, function)}.
     * @param subscription the subscription to unsubscribe.
     * @param unsubscribeProps an object to be merged with the unsubscribe message
     * @param unsubscribeCallback a function to be invoked when the unsubscription is acknowledged
     */
    this.unsubscribe = function(subscription, unsubscribeProps, unsubscribeCallback)
    {
        if (arguments.length < 1)
        {
            throw 'Illegal arguments number: required 1, got ' + arguments.length;
        }
        if (_isDisconnected())
        {
            throw 'Illegal state: already disconnected';
        }

        if (_isFunction(unsubscribeProps))
        {
            unsubscribeCallback = unsubscribeProps;
            unsubscribeProps = undefined;
        }

        // Remove the local listener before sending the message
        // This ensures that if the server fails, this client does not get notifications
        this.removeListener(subscription);

        var channel = subscription.channel;
        // Only send the message to the server if this client unsubscribes the last subscription
        if (!_hasSubscriptions(channel))
        {
            var bayeuxMessage = {
                channel: '/meta/unsubscribe',
                subscription: channel,
                _callback: unsubscribeCallback
            };
            var message = this._mixin(false, {}, unsubscribeProps, bayeuxMessage);
            _queueSend(message);
        }
    };

    this.resubscribe = function(subscription, subscribeProps)
    {
        _removeSubscription(subscription);
        if (subscription)
        {
            return this.subscribe(subscription.channel, subscription.scope, subscription.callback, subscribeProps);
        }
        return undefined;
    };

    /**
     * Removes all subscriptions added via {@link #subscribe(channel, scope, callback, subscribeProps)},
     * but does not remove the listeners added via {@link addListener(channel, scope, callback)}.
     */
    this.clearSubscriptions = function()
    {
        _clearSubscriptions();
    };

    /**
     * Publishes a message on the given channel, containing the given content.
     * @param channel the channel to publish the message to
     * @param content the content of the message
     * @param publishProps an object to be merged with the publish message
     * @param publishCallback a function to be invoked when the publish is acknowledged by the server
     */
    this.publish = function(channel, content, publishProps, publishCallback)
    {
        if (arguments.length < 1)
        {
            throw 'Illegal arguments number: required 1, got ' + arguments.length;
        }
        if (!_isString(channel))
        {
            throw 'Illegal argument type: channel must be a string';
        }
        if (/^\/meta\//.test(channel))
        {
            throw 'Illegal argument: cannot publish to meta channels';
        }
        if (_isDisconnected())
        {
            throw 'Illegal state: already disconnected';
        }

        if (_isFunction(content))
        {
            publishCallback = content;
            content = publishProps = {};
        }
        else if (_isFunction(publishProps))
        {
            publishCallback = publishProps;
            publishProps = {};
        }

        var bayeuxMessage = {
            channel: channel,
            data: content,
            _callback: publishCallback
        };
        var message = this._mixin(false, {}, publishProps, bayeuxMessage);
        _queueSend(message);
    };

    /**
     * Returns a string representing the status of the bayeux communication with the Bayeux server.
     */
    this.getStatus = function()
    {
        return _status;
    };

    /**
     * Returns whether this instance has been disconnected.
     */
    this.isDisconnected = _isDisconnected;

    /**
     * Sets the backoff period used to increase the backoff time when retrying an unsuccessful or failed message.
     * Default value is 1 second, which means if there is a persistent failure the retries will happen
     * after 1 second, then after 2 seconds, then after 3 seconds, etc. So for example with 15 seconds of
     * elapsed time, there will be 5 retries (at 1, 3, 6, 10 and 15 seconds elapsed).
     * @param period the backoff period to set
     * @see #getBackoffIncrement()
     */
    this.setBackoffIncrement = function(period)
    {
        _config.backoffIncrement = period;
    };

    /**
     * Returns the backoff period used to increase the backoff time when retrying an unsuccessful or failed message.
     * @see #setBackoffIncrement(period)
     */
    this.getBackoffIncrement = function()
    {
        return _config.backoffIncrement;
    };

    /**
     * Returns the backoff period to wait before retrying an unsuccessful or failed message.
     */
    this.getBackoffPeriod = function()
    {
        return _backoff;
    };

    /**
     * Sets the log level for console logging.
     * Valid values are the strings 'error', 'warn', 'info' and 'debug', from
     * less verbose to more verbose.
     * @param level the log level string
     */
    this.setLogLevel = function(level)
    {
        _config.logLevel = level;
    };

    /**
     * Registers an extension whose callbacks are called for every incoming message
     * (that comes from the server to this client implementation) and for every
     * outgoing message (that originates from this client implementation for the
     * server).
     * The format of the extension object is the following:
     * <pre>
     * {
     *     incoming: function(message) { ... },
     *     outgoing: function(message) { ... }
     * }
     * </pre>
     * Both properties are optional, but if they are present they will be called
     * respectively for each incoming message and for each outgoing message.
     * @param name the name of the extension
     * @param extension the extension to register
     * @return true if the extension was registered, false otherwise
     * @see #unregisterExtension(name)
     */
    this.registerExtension = function(name, extension)
    {
        if (arguments.length < 2)
        {
            throw 'Illegal arguments number: required 2, got ' + arguments.length;
        }
        if (!_isString(name))
        {
            throw 'Illegal argument type: extension name must be a string';
        }

        var existing = false;
        for (var i = 0; i < _extensions.length; ++i)
        {
            var existingExtension = _extensions[i];
            if (existingExtension.name === name)
            {
                existing = true;
                break;
            }
        }
        if (!existing)
        {
            _extensions.push({
                name: name,
                extension: extension
            });
            this._debug('Registered extension', name);

            // Callback for extensions
            if (_isFunction(extension.registered))
            {
                extension.registered(name, this);
            }

            return true;
        }
        else
        {
            this._info('Could not register extension with name', name, 'since another extension with the same name already exists');
            return false;
        }
    };

    /**
     * Unregister an extension previously registered with
     * {@link #registerExtension(name, extension)}.
     * @param name the name of the extension to unregister.
     * @return true if the extension was unregistered, false otherwise
     */
    this.unregisterExtension = function(name)
    {
        if (!_isString(name))
        {
            throw 'Illegal argument type: extension name must be a string';
        }

        var unregistered = false;
        for (var i = 0; i < _extensions.length; ++i)
        {
            var extension = _extensions[i];
            if (extension.name === name)
            {
                _extensions.splice(i, 1);
                unregistered = true;
                this._debug('Unregistered extension', name);

                // Callback for extensions
                var ext = extension.extension;
                if (_isFunction(ext.unregistered))
                {
                    ext.unregistered();
                }

                break;
            }
        }
        return unregistered;
    };

    /**
     * Find the extension registered with the given name.
     * @param name the name of the extension to find
     * @return the extension found or null if no extension with the given name has been registered
     */
    this.getExtension = function(name)
    {
        for (var i = 0; i < _extensions.length; ++i)
        {
            var extension = _extensions[i];
            if (extension.name === name)
            {
                return extension.extension;
            }
        }
        return null;
    };

    /**
     * Returns the name assigned to this CometD object, or the string 'default'
     * if no name has been explicitly passed as parameter to the constructor.
     */
    this.getName = function()
    {
        return _name;
    };

    /**
     * Returns the clientId assigned by the Bayeux server during handshake.
     */
    this.getClientId = function()
    {
        return _clientId;
    };

    /**
     * Returns the URL of the Bayeux server.
     */
    this.getURL = function()
    {
        if (_transport && typeof _config.urls === 'object')
        {
            var url = _config.urls[_transport.getType()];
            if (url)
            {
                return  url;
            }
        }
        return _config.url;
    };

    this.getTransport = function()
    {
        return _transport;
    };

    this.getConfiguration = function()
    {
        return this._mixin(true, {}, _config);
    };

    this.getAdvice = function()
    {
        return this._mixin(true, {}, _advice);
    };

    // Use an alias to be less dependent on browser's quirks.
    org.cometd.WebSocket = window.WebSocket;
};

if (typeof define === 'function' && define.amd)
{
    define(function()
    {
        return org.cometd;
    });
}


/*
 * Copyright (c) 2008-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
(function()
{
    function bind($, org_cometd)
    {
        // Remap cometd JSON functions to jquery JSON functions.
        org_cometd.JSON.toJSON = JSON.stringify;
        org_cometd.JSON.fromJSON = JSON.parse;

        function _setHeaders(xhr, headers)
        {
            if (headers)
            {
                for (var headerName in headers)
                {
                    if (headerName.toLowerCase() === 'content-type')
                    {
                        continue;
                    }
                    xhr.setRequestHeader(headerName, headers[headerName]);
                }
            }
        }

        // Remap toolkit-specific transport calls.
        function LongPollingTransport()
        {
            var _super = new org_cometd.LongPollingTransport();
            var that = org_cometd.Transport.derive(_super);

            that.xhrSend = function(packet)
            {
                return $.ajax({
                    url: packet.url,
                    async: packet.sync !== true,
                    type: 'POST',
                    contentType: 'application/json;charset=UTF-8',
                    data: packet.body,
                    global: false,
                    xhrFields: {
                        // For asynchronous calls.
                        withCredentials: true
                    },
                    beforeSend: function(xhr)
                    {
                        // For synchronous calls.
                        xhr.withCredentials = true;
                        _setHeaders(xhr, packet.headers);
                        // Returning false will abort the XHR send.
                        return true;
                    },
                    success: packet.onSuccess,
                    error: function(xhr, reason, exception)
                    {
                        packet.onError(reason, exception);
                    }
                });
            };

            return that;
        }

        function CallbackPollingTransport()
        {
            var _super = new org_cometd.CallbackPollingTransport();
            var that = org_cometd.Transport.derive(_super);

            that.jsonpSend = function(packet)
            {
                $.ajax({
                    url: packet.url,
                    async: packet.sync !== true,
                    type: 'GET',
                    dataType: 'jsonp',
                    jsonp: 'jsonp',
                    data: {
                        // In callback-polling, the content must be sent via the 'message' parameter.
                        message: packet.body
                    },
                    beforeSend: function(xhr)
                    {
                        _setHeaders(xhr, packet.headers);
                        // Returning false will abort the XHR send.
                        return true;
                    },
                    success: packet.onSuccess,
                    error: function(xhr, reason, exception)
                    {
                        packet.onError(reason, exception);
                    }
                });
            };

            return that;
        }

        $.CometD = function(name)
        {
            var cometd = new org_cometd.CometD(name);

            // Registration order is important.
            if (org_cometd.WebSocket)
            {
                cometd.registerTransport('websocket', new org_cometd.WebSocketTransport());
            }
            cometd.registerTransport('long-polling', new LongPollingTransport());
            cometd.registerTransport('callback-polling', new CallbackPollingTransport());

            return cometd;
        };

        // The default cometd instance.
        $.cometd = new $.CometD();

        return $.cometd;
    }

    if (typeof define === 'function' && define.amd)
    {
        define(['jquery', 'org/cometd'], bind);
    }
    else
    {
        bind(jQuery, org.cometd);
    }
})();

;(function($) {
	var matchScoreSub = function(lotteryId, matchs) {
		this.lotteryId = lotteryId;
		this.matchs = matchs;
		this.sub();
	}
	matchScoreSub.prototype = {
		matchType:function() {
			return $.matchType({'lotteryId':this.lotteryId});
		},
		channel:function() {
			return '/public/match/' + this.matchType() + "/";
		},
		sub:function() {
			var len = this.matchs.length;
			var channels = [];
			for(var i=0; i<len; i++) {
				var matchStatus = this.matchs[i].status;
				var matchId = this.matchs[i].matchId;
				var matchType = this.matchType();
				var channel = this.channel() + matchId;
				var element = {
					"matchId":matchId + '',
					"matchType":matchType,
					"channel":channel
				};
				//赛事已结束则不发起订阅请求
				if(matchStatus == 5) {
					continue;
				}
				$("body").trigger("subscribeNewChannel", element);
			}
		}
	}
	
	$.extend({
		subMatchScore:function(lotteryId, matchIdList) {
			new matchScoreSub(lotteryId, matchIdList);
		}
	});
})(window.jQuery);
;(function($, undefined) {
	/**
	 * 微博列表
	 */
	var weiboList = function(element,options){
	    this.$el = $(element);
	    this.options = $.extend({}, $.fn.weiboList.defaults, options); 
	    this.initialized();
	};
	weiboList.prototype = {
	    initialized : function(){
	    	this.settings = {};
	    	$.extend(this.settings, LT);
	    	this.settings.delegateEvents(this);//继承events事件
	    	this.rend();
	    },
	    checkPost:function (){
	    	//渲染前先做一次数据过滤，过滤掉微博列表中已渲染过的数据
	    	var liMap = new Array();
	    	$("li[weibomsgli]",this.$el).each(function (){
	    		var liId = $(this).attr("_id").replace("post_","");
	    		liMap[liId] = liId;
	    	});
	    	var length = this.options.posts.length;
	    	var newPosts = new Array();
	    	for(var i=0; i < length; i++){
	    		if(!liMap[(this.options.posts)[i].id]){
	    			newPosts.push((this.options.posts)[i]);
	    		}
	    	}
	    	this.options.posts = newPosts;
	    	return this.options.posts.length;
	    },
	    rend : function(){
	    	if(!this.checkPost()){
	    		return ;//没有微博数据需要渲染
	    	}
	    	var weibos;
	    	if(this.options.formatWeiboDataFunction && 
	    			typeof(this.options.formatWeiboDataFunction) == 'function'){
	    		weibos = this.options.formatWeiboDataFunction(this.options.posts,this.options.weiboUserId);
	    	} else {
	    		weibos = this.formatWeiboData(this.options.posts,this.options.weiboUserId);
	    	}
	    	
	    	if(this.options.pendType && this.options.pendType == 'append'){
	    		
	    	} else {
	    		weibos.reverse();//使用prepend渲染微博需要把原有的时间降序改成升序
	    		this.$el.find("fieldset").remove();
	    		this.$el.prepend(this.options.lastUpdateView);//添加“XXX前，看到这里”
	    	}
	    	for(var i=0; i<weibos.length; i++){//渲染微博
	        	var $weibo = $($.mustache(this.options.$weiboMsgTmpl.html(),
	        			this.mustacheRendWeibo(weibos[i])));
	        	if(weibos[i].betScheme){//渲染微博方案
	        		$weibo.find('[data-type="scheme-view"]').schemeView({
	        			scheme:weibos[i].betScheme,
	        			tmpl:"#bet-scheme-tmpl"
	        		});
	        	}
	        	if(weibos[i].srcwb && weibos[i].srcwb.sourceBetScheme){//转发情况：渲染源微博方案
	        		$weibo.find('[data-type="source-scheme-view"]').schemeView({
	        			scheme:weibos[i].srcwb.sourceBetScheme,
	        			tmpl:"#bet-scheme-tmpl"
	        		});
	        	}
	        	$weibo.hide();
	        	if(this.options.pendType && this.options.pendType == 'append'){
		    		this.$el.append($weibo);//渲染更多微博
		    	} else {
		        	this.$el.prepend($weibo);//渲染新消息提醒块
		        }
		        $weibo.fadeIn(500);
		        //渲染表情和渲染赛事会替换dom结构的内容，必须先渲染这部分再给对象绑定事件，否则绑定事件会丢失
		        $weibo.emotionsToHtml();//渲染表情
		        $weibo.match$ToHtml(); //渲染$赛事链接
		        this.hideText($weibo.find('[_hidediv]'));// 截取文字
				if(weibos[i].likeUsers){//渲染赞微博用户
	        		$weibo.find('[_n="like"]').weiboPraise({
	        			likeUsers:weibos[i].likeUsers,
	        			postId:weibos[i].id
	        		});
	        	}
				//判断跟单按钮是否显示
				var now = new Date().Format("yyyy-MM-dd hh:mm:ss");
				if(weibos[i].betScheme && weibos[i].betScheme.offtime &&
						weibos[i].betScheme.offtime.replace("T"," ") > now){
					if(1 == weibos[i].type) {//给微博跟单按钮绑定数据
						$weibo.find('[_id="follow-real-weibo-btn"]')
							.find('.follow-button').data('post',weibos[i]);
					} else if(3 == weibos[i].type) {//给微博合买按钮绑定数据
						$weibo.find('[_id="follow-real-weibo-btn"]')
							.find('.groupbuy-button').data('post',weibos[i]);
					}
				} else {
					$weibo.find('[_id="follow-real-weibo-btn"]').hide();
				}
				//判断采纳按钮是否显示
				if(weibos[i].type != 2 || weibos[i].betScheme == null){//不是推荐或方案为空，不显示采纳
					$weibo.find('[_id="accept-recomend-weibo-btn"]').hide();
				}
				//渲染微博实单跟单用户
				if(weibos[i].type==1 && weibos[i].realFollowers
						&& weibos[i].realFollowers.length
						&& weibos[i].realFollowers.length> 0){
					var $realFollowUserDiv = $weibo.find('[_id="real-follower-div"]');
					$realFollowUserDiv.realWeiboFollowUsers({
						postId:weibos[i].id,
						realFollowers:weibos[i].realFollowers,
						weiboType:weibos[i].type
					});
				}
				//渲染微博实单合买用户
				if(weibos[i].type==3 && weibos[i].realFollowers
						&& weibos[i].realFollowers.length
						&& weibos[i].realFollowers.length> 0){
					var $groupBuyUserDiv = $weibo.find('[_id="real-follower-div"]');
					$groupBuyUserDiv.realWeiboFollowUsers({
						postId:weibos[i].id,
						realFollowers:weibos[i].realFollowers,
						weiboType:weibos[i].type
					});
				}
		        bindMouseInMatch$StringEvent($weibo);//绑定赛事浮框事件，必须放在hideText后面，否则绑定事件会失效
				bindMouseInHeadOrNicknameEvent($weibo);
	        }
	    },
	    reset : function(option){
	        this.options = $.extend({},this.options,option);
	        this.rend();
	    },
	    events : {
	    	'click [_n="forward"]' : 'forward',
	    	'click [_n="delete"]' : 'deleteWeibo',
	    	'click [_n="share"]' : 'share',
	    	'click [_n="favoriate"]' : 'favorite',
	    	'click [_n="comment"]' : 'comment',
	    	'click [_n="like"]' : 'like',
	    	'click .follow-button' : 'followRealWeibo',
	    	'click .groupbuy-button' : 'groupbuyWeibo',
	    	'click .accept-rec-button' : 'acceptRecWeibo',
	    },
	    forward : function (event){
	    	if(!this.options.weiboUserId ||
	    			this.options.weiboUserId < 1 ){
	    		this.gotoLogin();
	    		return ;
	    	}
			var $this = $(event.target).closest('[_n="forward"]');
			var _postId = $this.attr("_postId");
			$.post("http://www.davcai.com/showForward", {postId:_postId}, function(result, e) {
				if (result != null) {
					if (result.success) {
						$this.forwardDialog({
							forwardWeibo:result.results[0]
						});
					} else {
						alert(result.desc);
					}
				}
			}, 'jsonp').error(function(e) {
				alert( "操作失败，请刷新页面重试！");});
	    },
	    deleteWeibo : function (event){
	    	if(!this.options.weiboUserId ||
	    			this.options.weiboUserId < 1 ){
	    		this.gotoLogin();
	    		return ;
	    	}
	    	if(confirm("确定删除吗？")){
				var $this = $(event.target);
				var _$el = this.$el;
				var _postId = $this.attr("_postId");
				$.post("http://www.davcai.com/ajaxDelPost", {postId:_postId}, function(result, e) {
					if (result != null) {
						if (result.success) {
							$.msgbox("删除成功",$this,{success:false});
							setTimeout(function (){
								$("[_id=post_" + _postId + "]").each(function (){
									$(this).fadeOut(300,function (){
										var $next = $(this).next();
										if($next.is('fieldset')){
											$next.remove();
										}
										$(this).remove();
									});
									var a = $("li",_$el).length < 1;
									var b = /\/(\d{15})\/(\d{15})/.test(location.href);
									if( a  && b){
										window.location.href="/home";
									}
								});
							},300);
						} else {
							$.msgbox(result.desc,$this,{success:false});
						}
					}
				}, 'jsonp');
			}
	    },
	    share : function (event){
	    	if(!this.options.weiboUserId ||
	    			this.options.weiboUserId < 1 ){
	    		this.gotoLogin();
	    		return ;
	    	}
	    	var $this = $(event.target).closest('[_n="share"]');
			var $li = $this.closest('li');
	    	var weiboContent = $li.find('[_n="content"][_sourcediv]').text();
			$this.shareDialog({
				shareWeibo:weiboContent,
				weiboLinkUrl:$this.attr("_share_link")
			});
	    },
	    favorite : function (event){
	    	if(!this.options.weiboUserId ||
	    			this.options.weiboUserId < 1 ){
	    		this.gotoLogin();
	    		return ;
	    	}
	    	var $this = $(event.target), status = $this.attr("_s"), count = $this.attr("_count");
	    	$this.attr("disabled",true);
			if("favoriate" == status){
				$.post("http://www.davcai.com/addFavoriate", {postId : $this.attr("_postId")}, function(result, e) {
					if (result != null) {
						if (result.success) {
							if(result.desc=="true"){
								count++;
								$this.attr("_count",count);
							}else{
								$.msgbox("已经收藏过了",$this,{success:false});
							}
							$this.text('已收藏('+count+')');
							$this.attr("_s","unfavoriate");
							$.msgbox("已收藏",$this,{success:true});
						} else {
							$.msgbox(result.desc,$this,{success:false});
						}
					}
					$this.attr("disabled",false);
				}, 'jsonp');
			}else if("unfavoriate" == status){
				$.post("http://www.davcai.com/delFavoriate", {postId : $this.attr("_postId")}, function(result, e) {
					if (result != null) {
						if (result.success) {
							if (result.desc == "true") {
								count--;
								$this.attr("_count",count);
							}else{
								$.msgbox("未收藏",$this,{success:false});
							}
							if(count < 1){
								$this.text('收藏');
							}else{
								$this.text('收藏('+count+')');
							}
							$this.attr("_s","favoriate");
						} else {
							$.msgbox(result.desc,$this,{success:false});
						}
					}
					$this.attr("disabled",false);
				}, 'jsonp');
			}
			return;
	    },
	    comment : function (event){
	    	if(!this.options.weiboUserId ||
	    			this.options.weiboUserId < 1 ){
	    		this.gotoLogin();
	    		return ;
	    	}
	    	var cmt = $(event.target);
	    	var liTag = cmt.closest("li[weiboMsgLi]");
	    	var posi = liTag.find('[_id="weibo-footer-container"] .commentContainer');
	    	this.options.isLoadCommentList = true;//告诉commentContainer加载微博列表
	    	this.options.appendPosi = posi;//commentContainer渲染的位置
	    	this.options.postId = cmt.attr("_postid");//评论那一条微博的微博id
	    	$(event.target).commentContainer(this.options);
	    },
	    like : function (event){
	    	if(!this.options.weiboUserId ||
	    			this.options.weiboUserId < 1 ){
	    		this.gotoLogin();
	    		return ;
	    	}
	    	var $this = $(event.target), status = $this.attr("_s"), count = $this.attr("_count");
			var postId = $this.attr("_postId");
			if("like" == status){
				$.post(LT.Settings.URLS.like.like, {postId : postId}, function(result, e) {
					if (result != null) {
						if (result.success) {
							if(result.desc=="true"){
								count++;
								$this.attr("_count",count);
								$this.msgbox('已赞');
							}else{
								$this.msgbox('已赞过！');
							}
							$this.text('已赞('+count+')');
							$this.attr("_s","unLike");
							$this.weiboPraise({
								postId:postId,
								addMe:result.weiboUser,
								deleteMe:null
							});
						} else {
							$this.msgbox(result.desc);
						}
					}
					$this.attr("disabled",false);
				}, 'jsonp');
			} else if("unLike" == status){
				$.post(LT.Settings.URLS.like.unlike, {postId : postId}, function(result, e) {
					if (result != null) {
						if (result.success) {
							if (result.desc == "true") {
								count--;
								$this.attr("_count",count);
							} else {
								$this.msgbox('未赞！');
							}
							if(count < 1){
								$this.text('赞');
							} else {
								$this.text('赞('+count+')');
							}
							$this.attr("_s","like");
							$this.weiboPraise({
								postId:postId,
								deleteMe:result.weiboUser,
								addMe:null
							});
						} else {
							$this.msgbox(result.desc);
						}
					}
					$this.attr("disabled",false);
				}, 'jsonp');
			}
	    },
	    followRealWeibo : function (event){
	    	if(!this.options.weiboUserId ||
	    			this.options.weiboUserId < 1 ){
	    		this.gotoLogin();
	    		return ;
	    	}
	    	var $this = $(event.target);
	    	if(!$this.data("post")){//没有数据什么都不做
	    		return ;
	    	}
	    	$this.followRealWeiboDialog({
	    		post:$this.data("post"),
	    		appendPosi:$this.closest('li[weiboMsgLi]').find('[_id="weibo-footer-container"]'),
	    		currentUserId:this.options.weiboUserId
	    	});
	    },
	    groupbuyWeibo : function (event){//参与合买
	    	if(!this.options.weiboUserId ||
	    			this.options.weiboUserId < 1 ){
	    		this.gotoLogin();
	    		return ;
	    	}
	    	var $this = $(event.target);
	    	if(!$this.data("post")){//没有数据什么都不做
	    		return ;
	    	}
	    	$this.groupBuyRealWeiboDialog({
	    		post:$this.data("post"),
	    		appendPosi:$this.closest('li[weiboMsgLi]').find('[_id="weibo-footer-container"]'),
	    		currentUserId:this.options.weiboUserId
	    	});
	    },
	    acceptRecWeibo : function (event){
	    	if(!this.options.weiboUserId ||
	    			this.options.weiboUserId < 1 ){
	    		this.gotoLogin();
	    		return ;
	    	}
	    	var $this = $(event.target);
	    	var url = $this.attr("_url");
	    	window.open(url,'_blank');
	    },
	    formatWeiboData : function(posts,userId){//格式化微博数据，使得json数据符合模板
			var results = [];
			for(var i=0 ; i < posts.length ; i++ ){
				var obj = posts[i];
				var srcwb = false;
				if(!obj.user){
					continue;
				}
				if (obj.sourceWeiboMsg != null && obj.sourceWeiboMsg.id > 0) {
					srcwb = {
						id : obj.sourceWeiboMsg.id,
						ownerId : obj.sourceWeiboMsg.ownerId,
						nickName : obj.sourceUser.nickName,
						title : obj.sourceWeiboMsg.title || '',
						content : obj.sourceWeiboMsg.content || '',
						createTime : obj.sourceCreateTimeFmt,
						commentCount : obj.sourceWeiboMsg.commentCount,
						from : obj.sourceWeiboMsg.from || '',
						fromUrl : obj.sourceWeiboMsg.fromUrl || '',
						sourceBetScheme:obj.sourceBetScheme,
						sourceRealFollowers:obj.sourceRealFollowers,
						sourceType:obj.sourceType,
						certificationType:obj.user.certificationType
					};
				}
				results.push({
					id : obj.id,
					ownerId : obj.ownerId,
					nickName : obj.user.nickName || '',
					headImageURL : obj.user.headImageURL || '',
					title : obj.title || '',
					content : obj.content || '',
					srcwb : srcwb,
					createTime : obj.createTimeFmt,
					timeLine : obj.createTime,
					from :  obj.from || '',
					fromUrl : obj.fromUrl || '',
					forwardCount : obj.forwardCount,
					shareCount : obj.shareCount,
					favoriateCount : obj.favoriateCount,
					commentCount : obj.commentCount,
					likeCount : obj.likeCount,
					isFavourited : obj.favourited,
					isLike : obj.like,
					isCurrentUser : userId !=0 && userId == obj.ownerId,
					type : obj.type,
					schameId : obj.schameId,
					tags:obj.tags,
					likeUsers:obj.likeUsers,
					realFollowers:obj.realFollowers,
					betScheme:obj.betScheme,
					userRecords:obj.userRecords,
					certificationType:obj.user.certificationType,
					user:obj.user
				});
			}
			return results;
		},
	    mustacheRendWeibo:function(wb){
			return {
				posts: wb,
				forward: function(){
					return this.forwardCount > 0? '转发(' + this.forwardCount + ')' : '转发';
				},
				share: function(){
					return this.shareCount > 0 ? "分享("+ this.shareCount + ")": "分享";
				},
				favoriate: function(){
					var text = this.isFavourited ? '已收藏' : '收藏';
					return text + (this.favoriateCount > 0 ? "("+ this.favoriateCount + ")": '');
				},
				isfavoriated : function(){
					return this.isFavourited ? 'unfavoriate' : 'favoriate' ;
				},
				comment: function(){
					return this.commentCount > 0 ? "评论("+ this.commentCount + ")": "评论";
				},
				like: function(){
					var text = this.isLike ? '已赞' : '赞';
					return text + (this.likeCount > 0 ? "("+ this.likeCount + ")": '');
				},
				isliked: function(){
					return  this.isLike ? 'unLike' :  'like' ;
				},
				fromUrlPosi: function (){
					if(this.fromUrl){
						return '<a href="' + this.fromUrl +'" target="_blank">' + this.from + '</a>';
					} else {
						var from = this.from || '';
						return '<label>' + from + '</label>';
					}
				},
				srcFromUrl: function(){
					if(this.fromUrl){
						return '<a href="' + this.fromUrl +'" target="_blank">' + this.from + '</a>';
					} else {
						var from = this.from || '';
						return '<label>' + from + '</label>';
					}
				},
				shortCreateTime: function(){
					return $.shortTime(this.createTime);
				},
				followOrPartnerView:function() {
					var tpl = '<input class="btn-name" type="button" value="btn-view"/>';
					var btnName = "";
					var btnView = '';
					if(1 == this.type) {
						btnName = "follow-button";
						if(this.realFollowers && this.realFollowers.length > 0){
							btnView = '跟单('+this.realFollowers.length+')';
						} else {
							btnView = '跟单';
						}
					} else if(3 == this.type && this.betScheme && 
							(this.betScheme.saleStatus == 0)) {
						btnName = "groupbuy-button";
						btnView = '合买';
					} else {
						return "";
					}
					tpl = tpl.replace("btn-name", btnName);
					tpl = tpl.replace("btn-view", btnView);
					return tpl;
				},
//				followCountView: function (){
//					if(1 == this.type) {
//						if(this.realFollowers && this.realFollowers.length > 0){
//							return '跟单('+this.realFollowers.length+')';
//						} else {
//							return '跟单';
//						}
//					} else if(3 == this.type) {
//						return '合买';
//					}
//				},
				acceptWeiboBtnUrl:function (){
					if(this.type == 2 && this.betScheme != null){
						var url = 'http://trade.davcai.com/df/bet/';
						url +=  this.betScheme.lotteryId.toLowerCase() + "_" + this.betScheme.playId.toLowerCase() + ".html?";
						url += 'schemeId=' +  this.betScheme.id;
						return url;
					} else {
						return '';
					}
				},
				_tags:function() {
					var tags = this.tags;
					var result = [];
					for(index in tags) {
						if(tags[index].name == "过单率" && !tags[index].value) {
							continue;
						}
						result.push(tags[index]);
					}
					return result;
				}
			};
		},
		hideText : function($doms){
			$doms.each(function(){
				$(this).mlellipsis(7);
			});
		},
		gotoLogin : function (){
			$("#pop-outer").fadeIn(800);
			$("#all").show();
		}
	};
  
	$.fn.weiboList = function (option) {
		return this.each(function(input_field) {
			var $this = $(this), data = $this.data('weiboList');
		    if (!data) {
		    	$this.data('weiboList', (data = new weiboList(this, option)));
		    } else {
		    	data.reset(option);
		    }
		    //option 是方法名
		    if (typeof option == 'string'){
		    	if(!data[option]){
		    		console.log( 'Method ' + option + ' does not exist on jQuery.weiboList' );
		    	} else {
		    		data[option]();
		    	}
		    }
		});
	};
	$.fn.weiboList.defaults = {
		posts : null,
		pendType: 'prepend',
		weiboUserId:null,
		formatWeiboDataFunction:null,  //格式化微博数据方法，默认用formatWeiboData
		$weiboMsgTmpl:$("#weibo-msg-tmpl")  //渲染微博消息模版，默认用$("#weibo-msg-tmpl");
	};
})(window.jQuery);

;(function($) {
	/**
	 * 微博列表面板
	 */
	var weiboListPanel = function(element,options){
	    this.$el = $(element);
	    this.$newWeiboMsgTipTmpl = $("#new-weibo-msg-tip-tmpl");
	    this.$loadMoreWeiboTmpl = $("#load-more-weibo-tmpl");
	    this.$timeFieldSetTmpl = $("#time-field-set-tmpl");
	    this.$upLoading = $($("#loading-tmpl").html());
	    this.$downLoading = $($("#loading-tmpl").html());
	    this.pageIndex = 1;
	    this.isReadyAutoLoad = false;//微博初始化时，异步请求微博数据，此时不自动（滚动到底部触发）加载微博
	    this.options = $.extend({}, $.fn.weiboListPanel.defaults, options);
	    this.initialized();
	};
	weiboListPanel.prototype = {
	    initialized : function(){
	    	this.settings = {};
	    	$.extend(this.settings, LT);
	    	this.settings.delegateEvents(this);//绑定元素自身事件
	    	this.rend();
	    	this.bind();//绑定其他事件
	    },
	    rend : function(){
	        this.$newWeiboMsgTipBtn = $($.mustache(this.$newWeiboMsgTipTmpl.html(), {
	        	newWeiboCount:0
	        }));
	        this.$loadMoreWeiboBtn = $($.mustache(this.$loadMoreWeiboTmpl.html(), {
	        	newWeiboCount:0
	        }));
	        this.$el.parent().prepend(this.$newWeiboMsgTipBtn);//渲染新消息提醒块
	        this.$el.parent().append(this.$loadMoreWeiboBtn);//渲染加载更多微博块
	        this.$upLoading.show();
	        this.$el.parent().prepend(this.$upLoading);//上方添加loading图标
	        this.$el.parent().append(this.$downLoading);//下方添加loading图标
	        this.$downLoading.hide();
	        var self = this;
	        $.ajax({ //加载微博数据
	    		url:this.options.loadWeiboUrl,
	    		success : function(data, e) {
	    			self.options.posts = data.results;
	    			self.options.weiboUserId = data.userId;
	    			self.$upLoading.hide();//隐藏loading图标
	    			self.$el.weiboList(self.options);//渲染微博列表
	    			self.setTimeLine();
	    			self.lastUpdateTime = new Date().getTime();//设置最后更新时间
	    			self.isReadyAutoLoad = true;//加载微博完成，允许滚动到底部自动加载
	    		},
	    		error:function (data, e){
	    			$.showMessage(data.desc);
	    		},
	    		dataType:'json'
	    	});
	    },
	    bind :function (){
	    	var self = this;
	    	self.lastCheckTime = new Date().getTime();
	    	$(window).scroll({self:self},self.checkNewPost);
	    	$(window).scroll({self:self},self.autoLoadMorePost);
	    	$(window).mousemove({self:self},self.checkNewPost);
	    	this.$newWeiboMsgTipBtn.click({self:self},self.loadNewPost);
	    	this.$loadMoreWeiboBtn.click({self:self},self.loadMorePost);
	    },
	    events : {
	    	
	    },
	    reset : function(){
	        this.options = option;
	    },
	    // 初始化时间线
		setTimeLine : function(){
			if(this.$newWeiboMsgTipBtn.is(":hidden")){//如果是隐藏状态
				var $timeLine = this.$el.find('input[name="timeLine"]').first();
				if($timeLine.length ==0) {
					this.timeLine =  this.options.timeline;
				} else {//使用从微博列表中获取的timeline
					this.timeLine = parseInt($timeLine.val()) + 1;
					this.options.timeline = this.timeline;
				}
			};
		},
		// 检查新微博数
		checkNewPost:function(event){
			var self = event.data.self;
			self.setTimeLine(); //使用self因为调用该方法的有可能是$(window)对象
			var nowTime = new Date().getTime();
			if(self.options.timeOut > (nowTime - self.lastCheckTime)){
				return ;
			}
			self.lastCheckTime = nowTime;
			$.post(self.options.newWeiboTimerUrl, {timeLine : self.timeLine}, function(result, e) {
				if (result != null) {
					if (result.success) {
						if(result.resultCode > 0){
							var newPostCount = result.resultCode;
							self.$newWeiboMsgTipBtn.show();
							self.$newWeiboMsgTipBtn.find('[_id="new-weibo-count"]').html(newPostCount);
						}
					} else {
						$.showMessage(result.desc);
					}
				}
			}, 'jsonp');
		},
		loadNewPost:function (event){
			var self = event.data.self;
			$(this).hide();
			self.$upLoading.show();
			$.post(self.options.newWeiboUrl , {timeLine:self.timeLine}, function(result, e) {
				if (result != null) {
					if (result.success) {
						self.options.posts = result.results;
		    			self.options.weiboUserId = result.userId;
		    			self.options.pendType = 'prepend';
		    			self.options.lastUpdateView = self.rendLastUpdateTimeHtml();//取得“XXX前看到这里”html
						self.$el.weiboList(self.options);//渲染微博列表
						self.$upLoading.hide();
						self.lastUpdateTime = new Date().getTime();//刷新最后更新时间
					} else {
						$.showMessage(result.desc);
					}
				}
			}, 'jsonp');
		},
		autoLoadMorePost:function (event){
			var self = event.data.self;
			if(self.allLoadFinished){//已经全部加载完成
				return ;
			}
			if(self.isLoadingMorePost){
				return ;
			}
			if(!self.isReadyAutoLoad){
				return ;
			}
			if(self.isAtBottom() && 
					(self.pageIndex < self.options.autoLoadWeiboMaxPageIndex)){
				self.loadMorePost(event);
			} else if(self.isAtBottom() && self.pageIndex >= self.options.autoLoadWeiboMaxPageIndex){
				self.$loadMoreWeiboBtn.show();
			}
		},
		loadMorePost:function (event){
			var self = event.data.self;
			self.isLoadingMorePost = true;//设置为正在加载更多微博状态，防止不断加载
			self.$downLoading.show();
			self.$loadMoreWeiboBtn.hide();
			$.post(self.options.loadWeiboUrl, {"pageRequest.pageIndex" :self.pageIndex+1}, function(result, e) {
				if (result != null) {
					if (result.success) {
						self.pageIndex++;//当前页数+1
						self.options.posts = result.results;
		    			self.options.weiboUserId = result.userId;
		    			self.options.pendType = 'append'; //告诉weiboList插件渲染方式为append，默认是prepend
						self.$el.weiboList(self.options);//渲染微博列表
						self.$downLoading.hide();
						if(self.pageIndex > self.options.autoLoadWeiboMaxPageIndex){
							self.$loadMoreWeiboBtn.show();//当页面微博列表已经加载的页数等于autoLoadWeiboMaxPageIndex时，显示“加载更多微博”按钮
						}
						self.isLoadingMorePost = false;//设置为加载完成
						if(result.results.length < 1){
							self.allLoadFinished = true;
							self.$loadMoreWeiboBtn.show();
							self.$loadMoreWeiboBtn.html("没有更多微博了");
							return ;
						}
					} else {
						$.showMessage(result.desc);
					}
				}
			}, 'jsonp');
		},
		isAtBottom : function (){
			return $(window).scrollTop() + $(window).height() > $(document).height() - 110;
		},
		rendLastUpdateTimeHtml : function(){
			if(!this.lastUpdateTime){
				return '';
			}
			var millisecond = new Date().getTime() - this.lastUpdateTime;
			var timeView = "";
			if(millisecond < 60000){
				timeView = parseInt(millisecond /1000) + "秒";
			} else if(millisecond < 360000){
				timeView = parseInt(millisecond /60000) + "分钟";
			} else{
				timeView = parseInt(millisecond /360000) + "小时";
			}
			return $.mustache(this.$timeFieldSetTmpl.html(),{
				interval:timeView
			});
		}
	};
  
	$.fn.weiboListPanel = function (option) {
		return this.each(function(input_field) {
			var $this = $(this), data = $this.data('weiboListPanel');
		    if (!data) {
		    	$this.data('weiboListPanel', (data = new weiboListPanel(this, option)));
		    } else {
		    	data.reset(option);
		    }
		    //option 是方法名
		    if (typeof option == 'string'){
		    	if(!data[option]){
		    		console.log( 'Method ' + option + ' does not exist on jQuery.weiboListPanel' );
		    	} else {
		    		data[option]();
		    	}
		    }
		});
	};
	$.fn.weiboListPanel.defaults = {
		loadWeiboUrl : LT.Settings.URLS.weibo.loadPost,
		newWeiboUrl : LT.Settings.URLS.weibo.weibo_new_posts,
		timeline : new Date().getTime(),
		newWeiboTimerUrl : LT.Settings.URLS.weibo.newPostTimer,
		timeOut:5000,
		autoLoadWeiboMaxPageIndex:3,
		weiboUserId:null
	};
})(window.jQuery);

;
(function($, undefined) {
	/**
	 * 转发微博对话框
	 */
	var forwardDialog = function($this,options,$sourceTarget) {
		this.$body = $this;
		this.$sourceTarget = $sourceTarget;
		this.options = $.extend({}, $.fn.forwardDialog.defaults, options);
		this.$forwardDialogTmpl = $("#forward-dialog-tmpl").html();
		this.initialized();
	};
	forwardDialog.prototype = {
		initialized : function() {
			this.settings = {};
			$.extend(this.settings, LT);
			this.rend();
			this.settings.delegateEvents(this);
		},
		rend : function() {
			this.$body.append($.mustache(this.$forwardDialogTmpl,{}));//将转发模板添加到body
			this.$el = $('[_id="forwardDialog"]');//获取转发对话框
			this.$forwardWindow = this.$el.find('[_id="forwardModal"]'); // 具体的转发窗口
			this.$forwardContent = this.$el.find('[_id="forwardContent"]');
			$('[_id="message_face_forward"]').jqfaceedit({ //一次绑定转发表情
		    	txtAreaObj:this.$forwardContent,
		    	containerObj:$('#emotion_icons'),
		    	textareaid:"forwardContent",
		    	top:30,
		    	left:-40
	    	});
			WB_API.addAtWhoEvent(this.$forwardContent);//一次绑定@用户功能
			this.reset(this.options,this.$sourceTarget);
		},
		events : {
			'change [_id="forwardContent"]':'checkText', //绑定转发内容检查
			'blur [_id="forwardContent"]':'checkText',   //绑定转发内容检查
			'focus [_id="forwardContent"]':'checkText',  //绑定转发内容检查
			'keyup [_id="forwardContent"]':'checkText',  //绑定转发内容检查
			'click #forwardBtn':'forward'				 //绑定转发按钮
		},
		reset : function(option,$sourceTarget) {
			this.$sourceTarget = $sourceTarget;
			this.options = $.extend({}, this.options, option);
			this.show();
			this.checkText();
		},
		show : function (){
			var forwardWeibo = this.options.forwardWeibo;
			this.$el.find('[_id=postId]').val(forwardWeibo.id);
			if (forwardWeibo.postId > 0) {
				this.$forwardContent.val("​ //@" + forwardWeibo.user.nickName + " : "
						+ forwardWeibo.content);
			} else {
				this.$forwardContent.val("");
			}
			this.$forwardWindow.modal('show');
			this.$forwardContent.focus();
		},
		checkText:function (){//检查转发字数
			var _remainingWords =  this.options.forwardWordsMaxLength - this.$forwardContent.val().length;
			if(_remainingWords >= 0){
				this.$el.find('[_id="remainingWords"]').html("还可输入<em style='color:red;'>" + _remainingWords + "</em>字");
			} else {
				this.$el.find('[_id="remainingWords"]').html("<em style='color:red;'>已超出&nbsp" + (0-_remainingWords) + "</em>字");
			}
			return _remainingWords < 0;
		},
		// 转发微博
		forward : function(){
			var $forwradBtn = $("#forwardBtn");
			if(this.checkText()){
				this.$forwardContent.focus();
				$forwradBtn.msgbox('转发字数超出范围了', {success:false});
				return;
			}
			var _this = this;
			var _postId = this.options.forwardWeibo.id;
//			this.$forwardContent.val($("<div/>").text(this.$forwardContent.val()).html());
			var par = $('[_id="forwardDialog"]').serialize();
			$forwradBtn.attr("disabled",true);
			$.post("http://www.davcai.com/publish", par, function(result, e) {
				if (result != null) {
					if (result.success) {
						var postresult = result.results;
						if(postresult[0].id < 1){
							$forwradBtn.msgbox('转发失败', {success:false});
							return;
						}
						var $sbtn = _this.$sourceTarget;
						if($sbtn.length > 0){
							var count = $sbtn.attr("_s");
							count++;
							$sbtn.attr("_s",count);
							$sbtn.text("转发("+count+")");
						}
						$forwradBtn.msgbox('转发成功', {success:true});
						setTimeout(function (){
							_this.$forwardWindow.modal('hide')
						},300);
					} else {
						$forwradBtn.msgbox('转发失败', {success:false});
					}
				}
				$forwradBtn.attr("disabled",false);
			}, 'jsonp').error(function(e) {
				$forwradBtn.msgbox('转发失败', {success:false});;
				$forwradBtn.attr("disabled",false);
			});
		}
	};

	$.fn.forwardDialog = function(option) {
		return this.each(function(input_field) {
			var $this = $('body'),
				data = $this.data('forwardDialog');
		    if (!data) {
		    	$this.data('forwardDialog', (data = new forwardDialog($this, option,$(this))));
		    } else {
		    	data.reset(option,$(this));
		    }
			// option 是方法名
			if (typeof option == 'string') {
				if (!data[option]) {
					console.log('Method ' + option
							+ ' does not exist on jQuery.forwardDialog');
				} else {
					data[option]();
				}
			}
		});
	};
	$.fn.forwardDialog.defaults = {
		forwardWeibo : null,
		forwardWordsMaxLength : 1000
	};
})(window.jQuery);

;
(function($, undefined) {
	/**
	 * 分享微博对话框
	 */
	var shareDialog = function($this,options,$sourceTarget) {
		this.$body = $this;
		this.$sourceTarget = $sourceTarget;
		this.options = $.extend({}, $.fn.shareDialog.defaults, options);
		this.$shareDialogTmpl = $("#share-dialog-tmpl").html();
		this.initialized();
	};
	shareDialog.prototype = {
		initialized : function() {
			this.settings = {};
			$.extend(this.settings, LT);
			this.rend();
			this.settings.delegateEvents(this);
		},
		rend : function() {
			this.$body.append($.mustache(this.$shareDialogTmpl,{}));//将分享模板添加到body
			this.$el = $('[_id="shareDialog"]');//获取分享对话框
			this.$shareWindow = this.$el.find('[_id="shareModal"]'); // 具体的分享窗口
			this.$shareContent = this.$el.find('#shareContent');
			$('[_id="message_face_share"]').jqfaceedit({ //一次绑定分享表情
		    	txtAreaObj:this.$shareContent,
		    	containerObj:$('#emotion_icons'),
		    	textareaid:"shareContent",
		    	top:30,
		    	left:-40
	    	});
			WB_API.addAtWhoEvent(this.$shareContent);//一次绑定@用户功能
			this.reset(this.options,this.$sourceTarget);
		},
		events : {
			'change #shareContent':'checkText', //绑定分享内容检查
			'blur #shareContent':'checkText',   //绑定分享内容检查
			'focus #shareContent':'checkText',  //绑定分享内容检查
			'keyup #shareContent':'checkText',  //绑定分享内容检查
			'click #sharePostBtn':'share'				 //绑定分享按钮
		},
		reset : function(option,$sourceTarget) {
			this.$sourceTarget = $sourceTarget;
			this.options = $.extend({}, this.options, option);
			var shareWeibo = $.trim(this.options.shareWeibo);//微博内容
			if(this.options.weiboLinkUrl.length > 0 && "/" == this.options.weiboLinkUrl.substring(0,1)){
				this.options.weiboLinkUrl = this.options.weiboLinkUrl.substring(1);
			}
			this.tail = LT.Settings.LT_BASE_URL + this.options.weiboLinkUrl +  " (分享自@大V彩)";//分享的尾巴
			var beyondLength = shareWeibo.length + this.tail.length - this.options.shareWordsMaxLength;
			if(beyondLength > 0){
				shareWeibo = shareWeibo.substr(0,shareWeibo.length-beyondLength -3) + "…";
			}
			this.$shareContent.val(shareWeibo+ "  " + this.tail);
			this.show();
			this.checkText();
		},
		show : function (){
			this.$shareWindow.modal('show');
			this.$shareContent.focus();
		},
		checkText:function (){//检查分享字数
			if(!this.$shareContent.val()){
				return true;
			}
			var _remainingWords =  this.options.shareWordsMaxLength - this.$shareContent.val().length;
			if(_remainingWords >= 0){
				this.$el.find('#shareContentRemainingWords').html("还可输入<em style='color:red;'>" + _remainingWords + "</em>字");
			} else {
				this.$el.find('#shareContentRemainingWords').html("<em style='color:red;'>已超出&nbsp" + (0 - _remainingWords) + "</em>字");
			}
			return _remainingWords < 0;
		},
		// 分享微博
		share : function(event){
			var $forwradBtn = $("#shareBtn");
			if(this.checkText()){
				this.$shareContent.focus();
				return;
			}
			var sinaCheck = $('[_id="sinaWeiboCheck"]').attr("checked");
			if(sinaCheck != 'checked'){
				$('[_id="tipBindInfo"]').html("去绑定帐号或选勾已绑定帐号");
				return;
			}
			if($.trim(this.$shareContent.val()) == ""){
				this.$shareContent.focus();
				return;
			}
			var $shareBtn = $(event.target);
			var par = $('[_id="shareDialog"]').serialize();
			$shareBtn.attr("disabled",true);
			var _this = this;
			$.post("share", par, function(result, e) {
				if(result.success == true){
					$.msgbox(result.desc,$shareBtn,{success:true});
					setTimeout(function (){
						_this.$shareWindow.modal('hide');
					},300);
				} else {
					$.msgbox(result.desc,$shateBtn,{success:false});
				}
				$shareBtn.attr("disabled",false);
			},'json');
		}
	};

	$.fn.shareDialog = function(option) {
		return this.each(function(input_field) {
			var $this = $('body'),
				data = $this.data('shareDialog');
		    if (!data) {
		    	$this.data('shareDialog', (data = new shareDialog($this, option,$(this))));
		    } else {
		    	data.reset(option,$(this));
		    }
			// option 是方法名
			if (typeof option == 'string') {
				if (!data[option]) {
					console.log('Method ' + option
							+ ' does not exist on jQuery.shareDialog');
				} else {
					data[option]();
				}
			}
		});
	};
	$.fn.shareDialog.defaults = {
		shareWeibo : '',
		shareWordsMaxLength : 140,
		weiboLinkUrl:LT.Settings.LT_BASE_URL
	};
})(window.jQuery);

;(function($, undefined) {
	/**
	 * 评论容器
	 */
	var commentContainer = function(element,options){
	    this.$el = $(element);
	    this.options = $.extend({}, $.fn.commentContainer.defaults, options); 
	    this.$commentContainerTmpl = $("#comments-container-tmpl");
	    this.$loading = $($("#loading-tmpl").html());
	    this.initialized();
	};
	commentContainer.prototype = {
	    initialized : function(){
	    	this.settings = {};
	    	$.extend(this.settings, LT);
	    	this.settings.delegateEvents(this);//继承events事件
	    	this.rend();
	    },
	    rend : function(){
	    	this.$commentContainer = $($.mustache(this.$commentContainerTmpl.html(),{}));
	    	this.options.appendPosi.append(this.$commentContainer);
	    	if(this.$commentContainer.parent().hasClass('commentContainer')){
	    		this.options.appendPosi.parent().children("div[weibo-footer-element]").hide();
	    		this.$commentContainer.parent().show();//评论微博的评论框
	    	} else {
	    		this.$commentContainer.show();
	    	}
	    	this.adjustArrowPos();
	    	if(this.options.isLoadCommentList){//如果为true，则加载评论列表，默认false
		    	this.$commentContainer.commentList({
		    		$sourceTarget:this.$el,
		    		postId:this.options.postId
		    	});
	    	}
	    	this.bind();//绑定commentContainer事件
	    },
	    bind:function (){
	    	var commentTextArea = this.$commentContainer.find("textarea");
	    	//绑定$事件
	    	WB_API.addAtWhoEvent(commentTextArea[0]);
	    	//绑定@事件
	    	WB_API.addNickNameAtWho(commentTextArea[0]);
	    	//绑定点击赛事按钮事件
	    	WB_API.showMatchAreaDelegateEventsWith(commentTextArea.closest(".commentPostArea"));
	    	
	    	//为每一个评论框生成一个唯一ID
	    	var commentTextAreaId = commentTextArea.attr("id");
	    	if(!commentTextAreaId) {
	    		commentTextAreaId = new Date().getTime();
	    		commentTextArea.attr("id", commentTextAreaId);
	    	}
	    	//绑定插入表情事件
	    	commentTextArea.closest('.commentPostArea')
	    		.find(".editor>.selector>.face[comment_face]").jqfaceedit({
		        	txtAreaObj:$("#" + commentTextAreaId),
		        	containerObj:$('#emotion_icons'),
		        	textareaid:commentTextAreaId,
		        	top:30,
		        	left:-40
	        });
	    	//单击发布评论按钮事件
	    	this.$commentContainer.find('[_id="submit-comment-content"]').click({
	    		self:this
	    	},this.submitComment);
	    	this.$commentContainer.find('textarea').val(this.options.commentContent);
	    },
	    events : {
	    	
	    },
	    reset:function (option){
	    	if(this.$commentContainer.parent().hasClass('commentContainer')){
	    		this.options.appendPosi.parent().children(':not([comment-container-div])').hide();
	    		this.$commentContainer.parent().toggle();//评论微博的评论框
	    	} else {
	    		this.$commentContainer.toggle();//回复评论的回复框
	    	}
	    	this.adjustArrowPos();
	    },
	    adjustArrowPos : function (){
	    	var linkLeft = this.$el.offset().left;
	    	var offset = {};
	    	var textAreaLeft = 0;
	    	offset = this.$commentContainer.offset();
	    	if(offset){
	    		textAreaLeft = offset.left;
	    	} else {
	    		textAreaLeft = 500;
	    	}
	    	var left = linkLeft - textAreaLeft + this.$el.width()/2;
	    	this.$commentContainer.find('.arrow-top-out,.arrow-top-in').css({'left':left});
	    },
	    submitComment:function (event){
	    	var self = event.data.self;
    		var textarea = self.$commentContainer.find('textarea');
    		if (textarea.val()==''){
    			return;
    		}
    		var commentText = textarea.val();
    		if (!self.validateCommentText(commentText)){
    			return;
    		}
    		var data = {
    			pid: self.options.postId,
				forward: self.$commentContainer.find('input:checkbox').is(':checked'),
				comment: textarea.val()
			};
    		if(self.options.cid){
    			data = $.extend({},data,{cid:self.options.cid});
    		}
    		$.ajax(self.options.postCommentURL, {
    			dataType: 'jsonp',
    			method: 'post',
    			cache: false,
    			data: data,
    			success: function(result){
    				//评论完成后重新渲染评论列表
			    	self.$commentContainer.commentList({
			    		$sourceTarget:self.$el,
			    		postId:self.options.postId
			    	});
    			},
    			error: function(){
    				self.$loading.hide();
    				$.msgBox('评论发布失败', null, {pos:'center', success: false});
    			}
    		});
    		textarea.val('');
	    },
	    validateCommentText : function (commentText){
	    	if ($.isBlankStr(commentText)) {
	    		$.msgBox('请输入内容后再提交。', null, 
	    				{pos:'center', success: false});
	    		return false;
	    	}
	    	if (commentText.length>600){
	    		$.msgBox('评论字数超长，请节约用字，不要超过600字。', null, 
	    				{pos:'center', success: false});
	    		return false;
	    	}
	    	return true;
	    }
	};
  
	$.fn.commentContainer = function (option) {
		return this.each(function(input_field) {
			var $this = $(this), data = $this.data('commentContainer');
		    if (!data) {
		    	$this.data('commentContainer', (data = new commentContainer(this, option)));
		    } else {
		    	data.reset(option);
		    }
		    //option 是方法名
		    if (typeof option == 'string'){
		    	if(!data[option]){
		    		console.log( 'Method ' + option + ' does not exist on jQuery.commentContainer' );
		    	} else {
		    		data[option]();
		    	}
		    }
		});
	};
	$.fn.commentContainer.defaults = {		
		isLoadCommentList:false,
		postCommentURL:LT.Settings.URLS.comment.postComment,
		postId:null,
		cid:null,
		appendPosi:null,
		commentContent:''
	};
})(window.jQuery);

;(function($, undefined) {
	/**
	 * 评论列表
	 */
	var commentList = function(element,options){
	    this.$el = $(element);
	    this.options = $.extend({}, $.fn.commentList.defaults, options); 
	    this.$commentListTmpl = $("#comment-list-tmpl");
	    this.$loading = $($("#loading-tmpl").html());
	    this.initialized();
	};
	commentList.prototype = {
	    initialized : function(){
	    	this.settings = {};
	    	$.extend(this.settings, LT);
	    	this.settings.delegateEvents(this);//继承events事件
	    	this.$weiboMetaComment = this.options.$sourceTarget.closest('li[weiboMsgLi]').find('a[_n="comment"]');
	    	this.rend();
	    },
	    rend : function(){
	    	if(this.$el.find('.loading').length < 1){
	    		this.$el.append(this.$loading);
	    	}
	    	this.$loading.show();
	    	this.$post = this.$el.closest('li[weiboMsgLi]');
	    	this.loadComments(this.options.postId);
	    },
	    events:function (){
	    	
	    },
	    reset:function (option){
	    	this.options = $.extend({}, this.options, option);
	    	this.rend();
	    },
	    loadComments: function (postId){
	    	var self = this;
		    $.ajax(this.options.loadCommentUrl + '?pid='+postId, {
				dataType: 'jsonp',
				cache: false,
				success: function(result) {//评论获取成功后，渲染和绑定评论列表事件
					self.$loading.hide();
					//更新微博meta中评论数
					if(result.data.length > 0) {
						self.$weiboMetaComment.text("评论("+result.data.length+")");
		    		} else {
		    			self.$weiboMetaComment.text("评论");
		    		}
					var loginUserId = result.userId;
					var cmnts = {//渲染评论列表的数据对象
						comments: result.data,
						commentCount: result.data.length, 
						like: function(){
							return this.praised?"unlike":"like";
						},
						authorHomeURL: function(){
							return self.authorHomeURLById(this.author.weiboUserId);
						},
						praiseText: function(){
							var text = this.praised ? '已赞' : '赞';
							if (this.praisedCount>0){
								text += '(' + this.praisedCount + ')';
							}
							return text;
						},
						isOwner: function(){
							return this.author.weiboUserId == loginUserId;
						},
						createTimeLong: function(){
							var date = $.strToDate(this.createTime);
							return date.getTime();
						},
						createTimeSpan: function(){
							return $.shortTime(this.createTime);
						},
						replyTo: function(){
							if (this.repliedComment){
								var homeURL = self.authorHomeURLById(this.repliedUser.weiboUserId);
								return ' 回复 '+ '<a href="'+homeURL+'">'+this.repliedUser.nickName+'</a>';
							}else{
								return '';
							}
						}
					};
					var tpl = $($.mustache(self.$commentListTmpl.html(), cmnts));
					
					//转换评论中的@用户
					tpl.find(".cmt_con").each(function(index, elt) {
						$(elt).at();
					});
					//转换赛事链接
					tpl.match$ToHtml();
					//转换表情图标
					tpl.emotionsToHtml();
					
					tpl.find('li:last').addClass('last');
					self.addLikeHandler(tpl);
					self.addReplyHandler(tpl, self.$post, postId);
					self.addDeleteHandler(tpl);
					self.addHoverHandler(tpl);
					// 插入或替换DOM树
					var commentList = self.$post.find('.commentInfo');
					var hasComments = result.data.length;
					if (commentList.length > 0){
						commentList.replaceWith(tpl);
					}else if (hasComments>0){
						self.$post.find('.commentContainer').append(tpl);
					}
				},
				error: function(){
					self.$loading.hide();
					$.msgBox('评论列表加载失败', null, {pos:'center', success: false});
				}
			});
	    },
	    authorHomeURLById : function(weiboUserId){
			return '/'+weiboUserId+'/profile';
		},
	    addHoverHandler:function(comments){
	    	comments.find('.commentList li').hover(
	    		function(){
	    			$(this).find('.deleteComment').css({'visibility': 'visible'});
	    		},
	    		function(){
	    			$(this).find('.deleteComment').css({'visibility': 'hidden'});
	    		}
	    	);
	    },
	    addLikeHandler:function(comments){
	    	var like = comments.find('.like,.unlike');
	    	like.on('click', function(event){
	    		event.preventDefault();
	    		var self = $(this);
	    		var commentId = self.closest('li').attr('_comment');
	    		var praiseOrUnpraise = self.hasClass('like');
	    		$.ajax('http://www.davcai.com/prs_cmnt',{
	    			dataType: 'jsonp',
	    			data: {
	    				cid: commentId,
	    				add: praiseOrUnpraise
	    			},
	    			success: function(result){
	    				var count = parseInt(self.attr('like_count'));
	    				count = praiseOrUnpraise ? count+1 : Math.max(0,count-1);
	    				var likeText = '赞';
	    				if (praiseOrUnpraise){
	    					self.removeClass('like');
	    					self.addClass('unlike');
	    					likeText = '已赞';
	    				}else{
	    					self.removeClass('unlike');
	    					self.addClass('like');
	    					likeText = '赞';
	    				}
	    				self.attr('like_count', count);
	    				if (count>0){
	    					self.text(likeText+'('+count+')');
	    				}else {
	    					self.text(likeText);
	    				}
	    			},
	    			error: function(){
	    				$.msgBox('赞失败。', null, {pos:'center', success: false});
	    			}
	    		});
	    	});
	    },
	    addDeleteHandler:function(comments) {
	    	var self = this;
	    	var deleteLink = comments.find('.deleteComment');
	    	deleteLink.on('click', function(event){
	    		event.preventDefault();
	    		var $this = $(this);
	    		var commentId = $this.closest('li').attr('_comment');
	    		var cmntLi = $this.closest('li');
		    	var allCmnt = cmntLi.closest('.commentInfo');
		    	$.ajax('http://www.davcai.com/del_cmnt', {
		    		dataType: 'jsonp',
		    		data: {
		    			cid: commentId
		    		},
		    		success: function(result){
		    			if(result.success){
		    				$.msgBox('删除成功！', null, {pos:'center'});
		    				self.updateCmtCountInWeiboMeta(-1);
		    				var cmntCount = updateCommentCount(allCmnt);
		    				if (cmntCount == 0)
		    					allCmnt.remove();
		    				else
		    					cmntLi.remove();
		    			}else{
		    				$.msgBox(result.desc, null, {pos:'center', success: false});
		    			}
		    		},
		    		error: function(result){
		    			$.msgBox('因网络问题，删除失败。', null, {pos:'center', success: false});
		    		}
		    	});
	    	});
	    },
	    addReplyHandler:function(commentInfo, post, postId){
	    	var replies = commentInfo.find('.reply');
	    	var commentsLoading = post.find('.loading');
	    	replies.click(function (event){
	    		event.preventDefault();
	    		var $commentLi = $(this).closest('li');
	    		var commentContent = '回复@' + $commentLi.find('a[data-name]').attr('data-name') + ':';
		    	$(this).commentContainer({
		    		cid:$commentLi.attr('_comment'),
		    		postId:postId,
		    		appendPosi:$(this).closest('li'),
		    		isLoadCommentList:false,
		    		commentContent:commentContent
		    	});
	    	});
	    },
	    updateCmtCountInWeiboMeta : function(i){
	    	var $cmt = this.$weiboMetaComment;
	    	var cmntCountText = $cmt.text();
	    	var pt = /\((\d+)\)/;
	    	var groups = pt.exec(cmntCountText);
	    	var count = 0;
	    	if(null != groups){
	    		count = parseInt(groups[1]);
	    		count += i;
	    		if(count > 0) {
	    			$cmt.text("评论("+count+")");
	    		} else {
	    			$cmt.text("评论");
	    		}
	    	} else {
	    		$cmt.text("评论("+1+")");
	    	}
	    	return count;
	    }
	};
  
	$.fn.commentList = function (option) {
		return this.each(function(input_field) {
			var $this = $(this), data = $this.data('commentList');
		    if (!data) {
		    	$this.data('commentList', (data = new commentList(this, option)));
		    } else {
		    	data.reset(option);
		    }
		    //option 是方法名
		    if (typeof option == 'string'){
		    	if(!data[option]){
		    		console.log( 'Method ' + option + ' does not exist on jQuery.commentList' );
		    	} else {
		    		data[option]();
		    	}
		    }
		});
	};
	$.fn.commentList.defaults = {		
		loadCommentUrl:LT.Settings.URLS.comment.loadComments,
		postId:null,
		$sourceTarget:null
	};
})(window.jQuery);

;(function($, undefined) {
	/**
	 * 赞
	 */
	var weiboPraise = function(element,options){
	    this.$el = $(element);
	    this.options = $.extend({}, $.fn.weiboPraise.defaults, options); 
	    this.$praiseUserTmpl = $("#praise-user-tmpl");
	    this.$praiseContainerTmpl = $("#praise-container-tmpl");
	    this.initialized();
	};
	weiboPraise.prototype = {
	    initialized : function(){
	    	this.settings = {};
	    	$.extend(this.settings, LT);
	    	this.settings.delegateEvents(this);//继承events事件
	    	this.$post = this.$el.closest('li[weiboMsgLi]');
	    	this.rend();
	    },
	    rend : function(){
	    	var postId = this.options.postId;
	    	this.$container = this.$post.find('.list-open-praise');
	    	if(this.$container.length < 1){
	    		this.$container = $($.mustache(this.$praiseContainerTmpl.html(),{postId:postId}));
	    		this.$post.append(this.$container);
			}
			this.$container.hide();
	    	var users = this.options.likeUsers;
	    	if(!users){
	    		return '';
	    	}
			if(users.length > 0){
				var likeUserHtml = '这些人点了赞：';
				for ( var i = 0; i < users.length; i++) {
					if(i==0){ //第一位不需要顿号
						users[i].dot = "";
						users[i].postId = postId;
						likeUserHtml += $.mustache(this.$praiseUserTmpl.html(),users[i]);
					} else {
						users[i].dot = "、";
						users[i].postId = postId;
						likeUserHtml += $.mustache(this.$praiseUserTmpl.html(),users[i]);
					}
				}
				this.$container.html(likeUserHtml);
				this.$container.show();
			} else {
				this.$container.hide();
			}
	    },
	    reset : function(option){
	        this.options = $.extend({},this.options,option);
	        var users = this.options.likeUsers;
	        if(!users){
	        	users = this.options.likeUsers = new Array();
	        }
	        if(this.options.addMe){
	        	users.push(this.options.addMe);
	        } else if(this.options.deleteMe){
	        	for(var i=0; i<users.length; i++){
			        if(users[i].weiboUserId == this.options.deleteMe.weiboUserId){
			        	users.splice(i,1);
			        }
		        }
	        }
	        this.options.likeUsers = users;
	        this.rend();
	    },
	    events : {
	    	
	    }
	};
  
	$.fn.weiboPraise = function (option) {
		return this.each(function(input_field) {
			var $this = $(this), data = $this.data('weiboPraise');
		    if (!data) {
		    	$this.data('weiboPraise', (data = new weiboPraise(this, option)));
		    } else {
		    	data.reset(option);
		    }
		    //option 是方法名
		    if (typeof option == 'string'){
		    	if(!data[option]){
		    		console.log( 'Method ' + option + ' does not exist on jQuery.weiboPraise' );
		    	} else {
		    		data[option]();
		    	}
		    }
		});
	};
	$.fn.weiboPraise.defaults = {
		likeUsers:null,
		postId:null,
		deleteMe:null,
		addMe:null
	};
})(window.jQuery);

;(function($, undefined) {
	var MY_FOLLOWING_IDS;//插件全局使用对象
	var GlobalPostFollowersArray = {}; //全局微博实单跟单用户集合数组 GlobalPostFollowersArray[postId]=[followers1,followers2];
	var REAL_FOLLOWER_PAGE_SIZE = 15;
	/**
	 * 实单微博跟单用户
	 */
	var realWeiboFollowUsers = function(element,options){
	    this.$el = $(element);
	    this.options = $.extend({}, $.fn.realWeiboFollowUsers.defaults, options);
	    this.weiboType = options.weiboType;
	    this.weiboTypeName = (3 == options.weiboType) ? "合买" : "跟单"; 
	    this.$followUserTmpl = $("#real-weibo-follow-user-tmpl");
	    this.followStatisticTmpl = '<div style="margin:8px 10px 0px">' + this.weiboTypeName 
	    		+ '总人数：{{followCount}}，' + this.weiboTypeName 
	    		+ '总金额：{{buyCount}}{{totalAward}}</div>';
	    this.postPagerTmpl = '<div style="clear: both;" _followerPager id="postId_{{postId}}_follower_pager" _postId="{{postId}}">{{{pagerHtml}}}</div>';
	    this.initialized();
	};
	realWeiboFollowUsers.prototype = {
	    initialized : function(){
	    	if(!MY_FOLLOWING_IDS){
		    	$.ajax("http://www.davcai.com/aj_get_followingsids",{
		    		async:false,
		    		success:function (data){
		    			MY_FOLLOWING_IDS = data;
		    		},
		    		dataType:'json'
		    	});
	    	}
	    	if(!MY_FOLLOWING_IDS){//获取我关注的用户失败(可能是未登录)
	    		MY_FOLLOWING_IDS = [];//把我关注的人设置为0
	    	}
	    	this.settings = {};
	    	$.extend(this.settings, LT);
	    	this.settings.delegateEvents(this);//继承events事件
	    	this.$post = this.$el.closest('li[weiboMsgLi]');
	    	this.rend();
	    },
	    rend : function(){
	    	this.$myRealFollowersView = this.$el.find('#real_post_'+this.options.postId+'_my_followers');
	    	this.$realFollowersView = this.$el.find('#real_post_'+this.options.postId+'_followers');
	    	this.$myRealFollowersView.html(this.myRealFollowersView());
	    	this.$realFollowersView.html(this.realFollowersView());
	    	this.bind();
	    },
	    bind:function (){
	    	var self = this;
	    	//实单微博查看更多事件
			$("a[_muchUserLink]",this.$post).click(function (){
				var $this = $(this);
				var followersDiv = self.$realFollowersView;
				var myFollowersDiv = self.$myRealFollowersView;
				if(followersDiv.is(":hidden")){
					followersDiv.show();
					myFollowersDiv.hide();
				} else {
					myFollowersDiv.show();
					followersDiv.hide();
				}
			});
	    	$("a[data-page]",$("[_followerPager]",this.$post)).click(function (){
				var $this = $(this);
				self.bindFollowerPageEvent($this);
			});
	    },
	    reset : function(option){
	    	this.options = $.extend({}, this.options, option);
	    	this.rend();
	    },
	    events : {
	    	
	    },
	    myRealFollowersView:function (){
	    	var usersInfo = this.calRealFollowerByMyFollowings(this.options.realFollowers);
			if(usersInfo){
				var realFollowers = usersInfo.followUsers;
				if(realFollowers.length > 0){
					var html = "";
					for ( var i = 0; i < realFollowers.length && i < 3; i++) {
						if(realFollowers[i].afterTaxBonus && realFollowers[i].afterTaxBonus > 0){
							realFollowers[i].awardAmount = "奖金:<i>" + LTMath.roundNumber(realFollowers[i].afterTaxBonus,2) + "元</i>";
						} else {
							realFollowers[i].awardAmount ="";
						}
						if(!realFollowers[i].nickName){
							realFollowers[i].nickName = "匿名";
						}
						if(!realFollowers[i].headImageURL){
							realFollowers[i].headImageURL=LT.Settings.RESOURCE.DEFAULT_HEADER;
						}
						realFollowers[i].weiboType = this.weiboTypeName;
						html+=$.mustache(this.$followUserTmpl.html(),realFollowers[i]);
					}
					var htmlSecond = this.getMyFollowStatisticHtml(usersInfo.followUsersName);;
					htmlSecond += this.getMutchFollowHtml(this.options.postId);
					html= htmlSecond + "<ul class='list-open-form'>"+ html + "</ul>";
					return '<div class="list-open">' + html + '</div>';
				} else if(usersInfo.notFollowUsers && usersInfo.notFollowUsers.length >0){
					var html = "";
					realFollowers = usersInfo.notFollowUsers;
					for ( var i = 0; i < realFollowers.length && i < 3; i++) {
						if(realFollowers[i].afterTaxBonus && realFollowers[i].afterTaxBonus > 0){
							realFollowers[i].awardAmount = "奖金:<i>" + LTMath.roundNumber(realFollowers[i].afterTaxBonus,2) + "元</i>";
						} else {
							realFollowers[i].awardAmount ="";
						}
						if(!realFollowers[i].nickName){
							realFollowers[i].nickName = "匿名";
						}
						if(!realFollowers[i].headImageURL){
							realFollowers[i].headImageURL="http://www.davcai.com/images/default_face.jpg";
						}
						realFollowers[i].weiboType = this.weiboTypeName;
						html+=$.mustache(this.$followUserTmpl.html(),realFollowers[i]);
					}
					var htmlSecond = "";
					if(realFollowers.length > 3){
						htmlSecond = this.getMutchFollowHtml(this.options.postId);
					}
					html= this.getFollowStatisticHtml(realFollowers) + htmlSecond + "<ul class='list-open-form'>"+ html + "</ul>";
					return '<div class="list-open">' + html + '</div>';
				}
				return '';
			}
			return '';
	    },
	    realFollowersView:function (){ //全部跟单用户信息
			var usersInfo = this.calRealFollowerByMyFollowings(this.options.realFollowers);
			if(usersInfo){
				var realFollowers = usersInfo.followUsers.concat(usersInfo.notFollowUsers);
				GlobalPostFollowersArray[this.options.postId] = realFollowers; //把该实单微博跟单用户存入全局
				if(realFollowers.length > 0 && realFollowers.length <= REAL_FOLLOWER_PAGE_SIZE){//小于1页，不分页
					return '<div class="list-open">' + this.renderRealFollowers(realFollowers,usersInfo.followUsersName,this.options.postId) + '</div>';
				} else if(realFollowers.length > 0 && realFollowers.length > REAL_FOLLOWER_PAGE_SIZE){ //长度大于每页最大值，分页
					return '<div class="list-open">' +  this.renderRealFollowersByPage(realFollowers,usersInfo.followUsersName,this.options.postId)  + '</div>';
				} else {
					return '';
				}
				return '';
			}
			return '';
		},
		calRealFollowerByMyFollowings : function (realFollowers){//计算跟单用户中我关注的用户和我未关注的用户			
	    	var usersInfo = {};
			var followUsers = new Array();
			var followUsersName = new Array();//存储我关注的人也跟了此单:昵称
			var followUsersNameMap = {};//存储我关注的人也跟了此单:userId-昵称
			var notFollowUsers = new Array();
			
			if(realFollowers && realFollowers.length > 0){
				var myFollowingsIdMap = {};
				if(MY_FOLLOWING_IDS && MY_FOLLOWING_IDS.length > 0){
					for (var i = 0; i < MY_FOLLOWING_IDS.length; i++) {
						myFollowingsIdMap[MY_FOLLOWING_IDS[i]]=MY_FOLLOWING_IDS[i];
					}
				}
				for ( var i = 0; i < realFollowers.length; i++) {
					var userId = realFollowers[i].weiboUserId;
					if(myFollowingsIdMap[userId] && myFollowingsIdMap[userId] > 0){
						followUsers.push(realFollowers[i]);
						if(followUsersNameMap[userId]){
							
						} else {
							followUsersNameMap[userId] = realFollowers[i].nickName;
							followUsersName.push(realFollowers[i].nickName);
						}
					} else {
						notFollowUsers.push(realFollowers[i]);
					}
				}
			}
			usersInfo.followUsers = followUsers;
			usersInfo.notFollowUsers = notFollowUsers;
			usersInfo.followUsersName = followUsersName;
			return usersInfo;
		},
		getMutchFollowHtml:function(postId){
			return '<a href="javascript:void(0);"  style="float:right;margin-top: -20px;margin-right: 25px;"  _muchUserLink _postId="'+postId+'">查看更多</a>';
		},
		getLessFollowHtml:function(postId){
			return '<a style="float:right;margin-top: -20px;margin-right: 25px;" href="javascript:void(0);" _muchUserLink _postId="'+postId+'">收起</a>';
		},
		getMyFollowStatisticHtml:function(followUsersName){
			var userNames = followUsersName;
			var htmlSecond = "";
			if(userNames && userNames.length > 0){
				htmlSecond += '<div  style="margin:5px 10px 0px">我关注的：';
				for(var i=0; i< userNames.length - 1 && i < 2; i++){
					htmlSecond += userNames[i] + "、";
				}
				if(userNames.length >= 3 ){
					htmlSecond += userNames[2];
				} else {
					htmlSecond += userNames[userNames.length -1];
				}
				if(this.weiboType == 3) {
					htmlSecond += "等" + userNames.length + "人参与合买了此单。</div>";
				} else {
					htmlSecond += "等" + userNames.length + "人跟了此单。</div>";
				}
			}
			return htmlSecond;
		},
		getFollowStatisticHtml:function(realFollowers){
			var followCount = 0;
			var buyCount = 0;
			var totalAward = 0;
			for ( var i = 0; i < realFollowers.length; i++) {
				followCount++;
				buyCount += parseInt(realFollowers[i].buyAmount);
				totalAward += realFollowers[i].afterTaxBonus;
			}
			var htmlFirst = $.mustache(this.followStatisticTmpl,{
				followCount:followCount,
				buyCount:buyCount + " 元",
				totalAward:totalAward && totalAward > 0 ? "," + this.weiboTypeName + "总奖金：￥"+LTMath.roundNumber(totalAward,2):""
			});
			return htmlFirst;
		},
		renderRealFollowers:function(realFollowers,followUsersName,postId){
			var html = "";
			for ( var i = 0; i < realFollowers.length; i++) {
				if(realFollowers[i].afterTaxBonus && realFollowers[i].afterTaxBonus > 0){
					realFollowers[i].awardAmount = "奖金:<i>" + LTMath.roundNumber(realFollowers[i].afterTaxBonus,2) + "元</i>";
				} else {
					realFollowers[i].awardAmount ="";
				}
				if(!realFollowers[i].nickName){
					realFollowers[i].nickName = "匿名";
				}
				if(!realFollowers[i].headImageURL){
					realFollowers[i].headImageURL="http://www.davcai.com/images/default_face.jpg";
				}
				realFollowers[i].weiboType = this.weiboTypeName;
				html+=$.mustache(this.$followUserTmpl.html(),realFollowers[i]);
			}
			html= this.getFollowStatisticHtml(realFollowers) +
			 	this.getLessFollowHtml(postId) +
				this.getMyFollowStatisticHtml(followUsersName) + 
				"<ul  class='list-open-form'>"+ html + "</ul>";
			return html;
		},
		renderRealFollowersByPage:function (realFollowers,followUsersName,postId){
			var html = "";
			for ( var i = 0; i < REAL_FOLLOWER_PAGE_SIZE; i++) {
				if(realFollowers[i].afterTaxBonus && realFollowers[i].afterTaxBonus > 0){
					realFollowers[i].awardAmount = "奖金:<i>" + LTMath.roundNumber(realFollowers[i].afterTaxBonus,2) + "元</i>";
				} else {
					realFollowers[i].awardAmount ="";
				}
				html+=$.mustache(this.$followUserTmpl.html(),realFollowers[i]);
			}
			var totalPages = parseInt((realFollowers.length + REAL_FOLLOWER_PAGE_SIZE -1)/REAL_FOLLOWER_PAGE_SIZE);
			var pageHtml = $.mustache(this.postPagerTmpl,{
				postId:postId,
				pagerHtml:ajaxPager("javascript:void(0);",1,totalPages)
			});
			html= this.getFollowStatisticHtml(realFollowers) +  
				this.getLessFollowHtml(postId) +
				this.getMyFollowStatisticHtml(followUsersName) + 
				"<div><ul  class='list-open-form' id='"+postId+"_follow_users_div'>"+ html + "</ul></div>" + pageHtml;
			return html;
		},
		bindFollowerPageEvent:function ($this){
			var self = this;
			var followersDiv =  $this.closest("li[weiboMsgLi]").find("[realPostFollowers] ul[class='list-open-form']");
			var pageDiv = $this.closest("div[_followerPager]");
			var postId = pageDiv.attr("_postId");
			var pageNum = parseInt($this.attr("data-page"));
			var realFollowers = GlobalPostFollowersArray[postId];
			
			var html = "";
			for (var i = REAL_FOLLOWER_PAGE_SIZE*(pageNum-1),j=0;i < realFollowers.length && j<REAL_FOLLOWER_PAGE_SIZE; i++,j++) {
				if(realFollowers[i].afterTaxBonus && realFollowers[i].afterTaxBonus > 0){
					realFollowers[i].awardAmount = "奖金:<i>" + LTMath.roundNumber(realFollowers[i].afterTaxBonus,2) + "元</i>";
				} else {
					realFollowers[i].awardAmount ="";
				}
				html+=$.mustache(this.$followUserTmpl.html(),realFollowers[i]);
			}
			var totalPages = parseInt((realFollowers.length + REAL_FOLLOWER_PAGE_SIZE -1)/REAL_FOLLOWER_PAGE_SIZE);
			var pageHtml = ajaxPager("javascript:void(0);",pageNum,totalPages);
			followersDiv.html(html);
			pageDiv.html(pageHtml);
			bindMouseInHeadOrNicknameEvent(followersDiv);
			$("a[data-page]",pageDiv).click(function (){
				var $this = $(this);
				self.bindFollowerPageEvent($this);
			});
		}
	};
  
	$.fn.realWeiboFollowUsers = function (option) {
		return this.each(function(input_field) {
			var $this = $(this), data = $this.data('realWeiboFollowUsers');
		    if (!data) {
		    	$this.data('realWeiboFollowUsers', (data = new realWeiboFollowUsers(this, option)));
		    } else {
		    	data.reset(option);
		    }
		    //option 是方法名
		    if (typeof option == 'string'){
		    	if(!data[option]){
		    		console.log( 'Method ' + option + ' does not exist on jQuery.realWeiboFollowUsers' );
		    	} else {
		    		data[option]();
		    	}
		    }
		});
	};
	$.fn.realWeiboFollowUsers.defaults = {
		postId:null,
		realFollowers:null
	};
})(window.jQuery);

;(function($, undefined) {
	/**
	 * 方案视图
	 */
	var schemeView = function(element,options){
	    this.$el = $(element);
	    this.options = $.extend({}, $.fn.schemeView.defaults, options);
	    this.$schemeTmpl = $(this.options.tmpl);
	    this.initialized();
	};
	schemeView.prototype = {
	    initialized : function(){
	    	this.settings = {};
	    	$.extend(this.settings, LT);
	    	this.settings.delegateEvents(this);//继承events事件
	    	this.rend();
	    },
	    rend : function(){
	    	var betScheme = this.options.scheme;
	    	var matchs = betScheme.matchs;
	    	if(!betScheme || betScheme.id <= 0 || !matchs || matchs.length <= 0) {
	    		return;
	    	}
	    	
	    	var self = this;
	    	var betScheme = this.options.scheme;
	    	var showConcede = betScheme.showConcede;
	    	var playId = betScheme.playId;
	    	var betWidthRatio = '';
	    	var matchWidthRatio = '';
	    	if(playId.indexOf("05_ZC") != 0 &&
					playId.indexOf("10_LC") != 0) {
	    		if(showConcede != true) {
	    			betWidthRatio = 'bet-long match-long';
	    		} else {
	    			betWidthRatio = 'bet-short match-short';
	    		}
			} else {
				if(showConcede != true) {
					betWidthRatio = 'bet-min';
				}
			}
	    	
	    	var $scheme = $.mustache(this.$schemeTmpl.html(), {
	    		"scheme" : betScheme,
	    		"_pushMatchType":function() {
	    			var matchType = $.matchType({'lotteryId':betScheme.lotteryId});
	    			return matchType;
	    		},
	    		"_score":function() {
	    			if(this.status == 1) {
	    				return '未开赛';
	    			}
	    			return this.score;
	    		},
	    		"_resetScoreCss":function() {
	    			if(this.status == 1) {
	    				return '';
	    			}
	    			return true;
	    		},
	    		"_concedePoints":function() {//显示让球数
	    			if(!this.concedePoints || 
	    					'null' == this.concedePoints) {
	    				return '';
	    			}
	    			if(this.playId.indexOf("01_ZC") != 0 &&
	    					this.playId.indexOf("06_LC") != 0) {
	    				return '';
	    			}
	    			if(this.concedePoints > 0) {
	    				return "+" + this.concedePoints;
	    			}
	    			return this.concedePoints;
	    		},
	    		"_betContent" : function() {//对于保密方案，投注内容的显示
	    			if(!this.betCode) {
	    				return "保密";
	    			}
	    			return this.betOptions;
	    		},
	    		"throughResult" : function() {//实单方案投注选项命中赛果
	    			if(this.matchWin == true) {
	    				if(playId.indexOf("05_ZC") != 0 &&
	    						playId.indexOf("10_LC") != 0 &&
	    						showConcede != true) {
	    					if(this.betOptions.length <= 10) {
	    						return 'red-through-max';
	    					} else if(this.betOptions.length <= 22) {
	    						return "red-through-middle";
	    					}
	    				}
	    				return "red-through";
	    			}
	    		},
	    		"winprize":function() {//判断是否显示中奖图标
	    			if(this.status == 5203 || this.status == 12) {
	    				return true;
	    			}
	    			return '';
	    		},
	    		"bravery":function() {//胆码角标显示
	    			if(this.seed == true) {
	    				return "dan";
	    			}
	    			return '';
	    		},
	    		//推荐不显示“过关，倍数，方案金额”
	    		"_realScheme":function() {
	    			if(this.realScheme == true) {
	    				return '';
	    			}
	    			return true;
	    		},
	    		"_showConcede":function() {//是否显示让球
	    			if(showConcede == true) {
	    				return '';
	    			}
	    			return 'true';
	    		},
	    		"isMixPlay":function() {
	    			if(playId.indexOf("05_ZC") == 0 ||
	    					playId.indexOf("10_LC") == 0) {
	    				return '';
	    			}
	    			return true;
	    		},
	    		"formatCss":function() {
	    			return betWidthRatio;
	    		},
	    		"_type":function() {
	    			if(this.type == 1) {return '';}
	    			return true;
	    		},
	    		"_floorRatio":function() {
	    			var result = (this.floorAmount*100)/this.totalAmount + "";
	    			if(result && result > 0) {
	    				if(result.length > 2) {
	    					result = new Number(result).toFixed(2);
	    				}
	    				result += "%";
	    			} else {
	    				result = "无";
	    			}
	    			return result;
	    		},
	    		switchHomeGuest:function() {
	    			if(this.lotteryId == "JCLQ") {
	    				return '客队VS主队';
	    			}
	    			return '主队VS客队';
	    		}
	    	});
	    	this.$el.append($scheme);
	    	
	    	new $.subMatchScore(betScheme.lotteryId, betScheme.matchs);
	    },
	    reset : function(option){
	        this.options = $.extend({},this.options,option);
	        this.rend();
	    },
	    events : {
	    	
	    }
	};
  
	$.fn.schemeView = function (option) {
		return this.each(function(input_field) {
			var $this = $(this), data = $this.data('schemeView');
		    if (!data) {
		    	$this.data('schemeView', (data = new schemeView(this, option)));
		    } else {
		    	data.reset(option);
		    }
		    //option 是方法名
		    if (typeof option == 'string'){
		    	if(!data[option]){
		    		console.log( 'Method ' + option + ' does not exist on jQuery.schemeView' );
		    	} else {
		    		data[option]();
		    	}
		    }
		});
	};
	$.fn.schemeView.defaults = {
		scheme : null,
	};
})(window.jQuery);

;(function($, undefined) {
	/**
	 * 微博实单跟单窗口
	 */
	var followRealWeiboDialog = function(element,options){
	    this.$el = $(element);
	    this.options = $.extend({}, $.fn.followRealWeiboDialog.defaults, options); 
	    this.$followRealWeiboDialogTmpl = $("#weibo-real-follow-form-tmpl");
	    this.initialized();
	};
	followRealWeiboDialog.prototype = {
	    initialized : function(){
	    	this.settings = {};
	    	$.extend(this.settings, LT);
	    	this.settings.delegateEvents(this);//继承events事件
	    	this.rend();
	    },
	    rend : function(){
	    	var post = this.options.post;
	    	this.$followRealWeiboDialog = $($.mustache(this.$followRealWeiboDialogTmpl.html(),{
				postId:post.id,
				followRealWeiboContent:"我跟了  @" + post.nickName +" 发起的晒单方案。",
				schemeId:post.betScheme.id,
				maxBonus:LTMath.roundNumber(post.betScheme.maxBonus,2),
				totalAmount:LTMath.roundNumber(post.betScheme.totalAmount,2),
				danbeiAmount:post.betScheme.totalAmount/post.betScheme.multiple
			}));
	    	this.options.appendPosi.append(this.$followRealWeiboDialog);
	    	this.options.appendPosi.children("div[weibo-footer-element]").hide();
	    	this.$followRealWeiboDialog.show();
	    	this.bind();//绑定followRealWeiboDialog事件
	    },
	    bind:function (){
	    	this.bindFollowFormEvent(this.$followRealWeiboDialog);
	    },
	    events : {
	    	
	    },
	    reset:function (option){
	    	this.options.appendPosi.children(':not([weibobetformdiv])').hide();
	    	this.$followRealWeiboDialog.toggle();
	    },
	    bindFollowFormEvent:function ($context){
	    	var self = this;
	    	//绑定插入表情事件、赛事事件、统计字数
	    	$(".editor>.selector>.face[follow_face]",$context).each(function (){
	    		var $this = $(this);
	    		var $textArea = $context.find("[weiboBetTextarea]");
	    		var txtId = $textArea.attr("id");
	    		if(!txtId) {
	    			txtId = new Date().getTime();
	    			$textArea.attr("id", txtId);
	    		}
	    		var postId = $this.attr("_postid");
	    		$this.jqfaceedit({
	    	    	txtAreaObj:$("#"+txtId),
	    	    	containerObj:$('#emotion_icons'),
	    	    	textareaid:txtId,
	    	    	top:30,
	    	    	left:-40
	    	    });
	    		//绑定$事件
	    		WB_API.addAtWhoEvent($("#"+txtId)[0]);
	    		//绑定@事件
	    		WB_API.addNickNameAtWho($("#"+txtId)[0]);
	    		//绑定点击赛事按钮事件
	    		WB_API.showMatchAreaDelegateEventsWith($("#"+txtId).closest("form"));
	    		
	    		var $restWordsDiv = $textArea.next();
	    		var followTextareaRestwords = function ($this){
	    			var length = $this.val().length;
	    			if(length > 1000){
	    				$restWordsDiv.html("还可以输入0个字符");
	    				$this.val($this.val().substring(0,1000));
	    			} else {
	    				$restWordsDiv.html("还可以输入"+(1000-length)+"个字符");
	    			}
	    		};
	    		$restWordsDiv.html("还可以输入"+(1000 - $textArea.val().length)+"个字符");
	    		$textArea.keyup(function (e){
	    			followTextareaRestwords($(this));
	    		});
	    		$textArea.change(function (e){
	    			followTextareaRestwords($(this));
	    		});
	    		$textArea.keydown(function (e){
	    			followTextareaRestwords($(this));
	    		});
	    		$textArea.click(function (){
	    			$(this).css("color","#000");
	    		});
	    	});
	    	
	    	//阻止浏览器回车自动提交表单
	    	$('input[_n="follow_multiple"]',$context).keydown(function (e){
	    		if(e.keyCode==13){
	    			e.preventDefault();
	    		}
	    	});
	    	//跟单倍数改变时，计算购买金额
	    	$('input[_n="follow_multiple"]',$context).keyup(function (e){
	    		var $this = $(this);
	    		var totalAmount = $this.attr("_danbeiAmount");
	    		var mul = $this.attr("value");
	    		if(!LTMath.isInteger(mul)){
	    			$this.attr("value","");
	    			$this.focus();
	    			return false;
	    		} else {
	    			if(parseInt(mul) > 9999){
	    				$this.attr("value","");
	    				alert("不能大于9999倍");
		    			$this.focus();
		    			return false;
	    			}
	    			var followAmount = parseInt(mul) *  totalAmount;
	    			if(followAmount > 1000000){
	    				var ex = (followAmount-1000000)/totalAmount;
	    				var left = (followAmount-1000000)%totalAmount;
	    				mul = parseInt(mul) - parseInt(ex) - (left > 0 ? 1:0);
	    				$this.attr("value",mul);
	    				alert("投注金额超出100万元，\n已将倍数重置为当前可投注的最大倍数");
		    			$this.focus();
		    			followAmount = mul * totalAmount;
	    			}
	    			var $form = $this.closest("form");
	    			$("[_followAmount]",$form).html(followAmount + "&nbsp;元");
	    		}
	    		if(e.keyCode==13){//通过触发“立即投注”按钮点击事件来提交表单
	    			e.preventDefault();
	    			$('input[_n="follow_schema_confirm_btn"]',$context).trigger("click");
	    		}
	    	});
	    	//实单微博确认跟单事件
	    	$('input[_n="follow_schema_confirm_btn"]',$context).click(function (){
	    		var $this = $(this);
	    		var $form = $this.closest("form");
	    		var mulEle = $('input[name="multiple"]',$form);
	    		var mul = mulEle.attr("value");
	    		if(!LTMath.isInteger(mul)){
	    			mulEle.attr("value","");
	    			mulEle.focus();
	    			return false;
	    		}
	    		if(!confirm("确认跟单？")){
	    			return false;
	    		} else {
	    			$.blockUI({  
	    			     overlayCSS:{backgroundColor:'#FFFFFF'},  
	    			     message:'方案已提交，正在处理中，请勿重复提交！',  
	    			     css:{  
	    				      backgroundColor:'#FFFFFF',  
	    				      height:50  
	    			     }  
	    		     }); 
	    			$.ajax({
	    				url: "http://trade.davcai.com/ac/betconfirm_in_weibo.do?jsonp=?", 
	    				data: $form.serialize(),
	    				success: function (json){
	    					var rs = json.data;
	    					$.unblockUI({
	    						onUnblock:function (){
	    							if(rs) {
	    								$.msgBox(rs.data, null, {pos:'center', success: rs.success});
	    							} else {
	    								$.msgBox('操作失败！', null, {pos:'center', success: false});
	    							}
	    							$context.hide();
	    							if(true == rs.success){
	    								self.updateFollowBtnCountInWeibo(1);
	    								self.updateFollowersList($context,$form);
	    							}
	    						}
	    					});
	    				},
	    				dataType:"jsonp"
	    			});
	    		}
	    	});
	    },
	    updateFollowBtnCountInWeibo : function(i){
	    	this.$weiboFollowBtn = this.options.appendPosi.closest("li[weiboMsgLi]").find('.follow-button');
	    	var value = this.$weiboFollowBtn.val();
	    	var pt = /\((\d+)\)/;
	    	var groups = pt.exec(value);
	    	var count = 0;
	    	if(null != groups){
	    		count = parseInt(groups[1]);
	    		count += i;
	    		if(count > 0) {
	    			this.$weiboFollowBtn.val("跟单("+count+")");
	    		} else {
	    			this.$weiboFollowBtn.val("跟单");
	    		}
	    	} else {
	    		this.$weiboFollowBtn.val("跟单("+1+")");
	    	}
	    	return count;
	    },
	    updateFollowersList : function ($context,$form){
	    	var self = this;
	    	var $realFollowUserDiv = $context.closest("li[weiboMsgLi]").find('[_id="real-follower-div"]');
			$.getJSON('http://login.davcai.com/ajax_get_weibouser.do?jsonp=?', function(data){
	    		if (data != null) {
				   var weiboUser = data.weiboUser;
				   var danbei = $form.find('input[_n="follow_multiple"]').attr('_danbeiAmount');
				   var mul = $form.find('input[_n="follow_multiple"]').val();
				   self.options.post.realFollowers.push({
						nickName:weiboUser.nickName,
						headImageURL:weiboUser.headImageURL,
						buyAmount:danbei * mul,
						weiboUserId:weiboUser.weiboUserId
					});
					$realFollowUserDiv.realWeiboFollowUsers({
						postId:self.options.post.id,
						realFollowers:self.options.post.realFollowers
					});
	    		}
			});
	    }
	};
  
	$.fn.followRealWeiboDialog = function (option) {
		return this.each(function(input_field) {
			var $this = $(this), data = $this.data('followRealWeiboDialog');
		    if (!data) {
		    	$this.data('followRealWeiboDialog', (data = new followRealWeiboDialog(this, option)));
		    } else {
		    	data.reset(option);
		    }
		    //option 是方法名
		    if (typeof option == 'string'){
		    	if(!data[option]){
		    		console.log( 'Method ' + option + ' does not exist on jQuery.followRealWeiboDialog' );
		    	} else {
		    		data[option]();
		    	}
		    }
		});
	};
	$.fn.followRealWeiboDialog.defaults = {		
		post:null,
		appendPosi:null,
		currentUserId:null
	};
})(window.jQuery);

;(function($, undefined) {
	/**
	 * 微博参与合买窗口
	 */
	var groupBuyRealWeiboDialog = function(element,options){
	    this.$el = $(element);
	    this.options = $.extend({}, $.fn.groupBuyRealWeiboDialog.defaults, options); 
	    this.$groupBuyRealWeiboDialogTmpl = $("#weibo-groupbuy-form-tmpl");
	    this.initialized();
	};
	groupBuyRealWeiboDialog.prototype = {
	    initialized : function(){
	    	this.settings = {};
	    	$.extend(this.settings, LT);
	    	this.settings.delegateEvents(this);//继承events事件
	    	this.rend();
	    },
	    rend : function(){
	    	var post = this.options.post;
	    	this.$groupBuyRealWeiboDialog = $($.mustache(this.$groupBuyRealWeiboDialogTmpl.html(),{
				postId:post.id,
				followRealWeiboContent:"我参与了  @" + post.nickName +" 发起的合买方案，",
				schemeId:post.betScheme.id,
				maxBonus:LTMath.roundNumber(post.betScheme.maxBonus,2),
				totalAmount:LTMath.roundNumber(post.betScheme.totalAmount,2),
				canBuyAmount:post.betScheme.totalAmount - post.betScheme.purchasedAmount,
				floorAmount:function() {
					if(post.betScheme.floorAmount <= 0) {
						return "无";
					}
					return post.betScheme.floorAmount + "元";
				},
				shareRatio:post.betScheme.shareRatio
			}));
	    	this.options.appendPosi.append(this.$groupBuyRealWeiboDialog);
	    	this.options.appendPosi.children("div[weibo-footer-element]").hide();
	    	this.$groupBuyRealWeiboDialog.show();
	    	this.bind();//绑定groupBuyRealWeiboDialog事件
	    },
	    bind:function (){
	    	this.bindFollowFormEvent(this.$groupBuyRealWeiboDialog);
	    },
	    events : {
	    	
	    },
	    reset:function (option){
	    	this.options.appendPosi.children(':not([weibobetformdiv])').hide();
	    	this.$groupBuyRealWeiboDialog.toggle();
	    },
	    bindFollowFormEvent:function ($context){
	    	var self = this;
	    	//绑定插入表情事件、赛事事件、统计字数
	    	$(".editor>.selector>.face[follow_face]",$context).each(function (){
	    		var $this = $(this);
	    		var $textArea = $context.find("[weiboBetTextarea]");
	    		var txtId = $textArea.attr("id");
	    		if(!txtId) {
	    			txtId = new Date().getTime();
	    			$textArea.attr("id", txtId);
	    		}
	    		var postId = $this.attr("_postid");
	    		$this.jqfaceedit({
	    	    	txtAreaObj:$("#"+txtId),
	    	    	containerObj:$('#emotion_icons'),
	    	    	textareaid:txtId,
	    	    	top:30,
	    	    	left:-40
	    	    });
	    		//绑定$事件
	    		WB_API.addAtWhoEvent($("#"+txtId)[0]);
	    		//绑定@事件
	    		WB_API.addNickNameAtWho($("#"+txtId)[0]);
	    		//绑定点击赛事按钮事件
	    		WB_API.showMatchAreaDelegateEventsWith($("#"+txtId).closest("form"));
	    		
	    		var $restWordsDiv = $textArea.next();
	    		var followTextareaRestwords = function ($this){
	    			var length = $this.val().length;
	    			if(length > 1000){
	    				$restWordsDiv.html("还可以输入0个字符");
	    				$this.val($this.val().substring(0,1000));
	    			} else {
	    				$restWordsDiv.html("还可以输入"+(1000-length)+"个字符");
	    			}
	    		};
	    		$restWordsDiv.html("还可以输入"+(1000 - $textArea.val().length)+"个字符");
	    		$textArea.keyup(function (e){
	    			followTextareaRestwords($(this));
	    		});
	    		$textArea.change(function (e){
	    			followTextareaRestwords($(this));
	    		});
	    		$textArea.keydown(function (e){
	    			followTextareaRestwords($(this));
	    		});
	    		$textArea.click(function (){
	    			$(this).css("color","#000");
	    		});
	    	});
	    	
	    	//阻止浏览器回车自动提交表单
	    	$("input[name='buyAmount']",$context).keydown(function (e){
	    		if(e.keyCode==13){
	    			e.preventDefault();
	    		}
	    	});
	    	
	    	//合买认购金额改变事件触发
	    	$("input[name='buyAmount']",$context).keyup(function (e){
	    		var $this = $(this);
	    		var canBuy = $("span[_n='canBuy']",$context).text(); 
	    		var buyAmount = $this.val();
	    		if(!/^\d+$/.test(buyAmount) && buyAmount) {
	    			$this.val(1);
	    		} else if(parseInt(buyAmount) > parseInt(canBuy)) {
	    			$this.val(canBuy);
	    		}
	    		
	    		if(e.keyCode==13){//通过触发“立即投注”按钮点击事件来提交表单
	    			e.preventDefault();
	    			$('input[_n="groupbuy_schema_confirm_btn"]',$context).trigger("click");
	    		}
	    	});
	    	//实单微博确认合买事件
	    	$('input[_n="groupbuy_schema_confirm_btn"]',$context).click(function (){
	    		var $this = $(this);
	    		var $form = $this.closest("form");
	    		var groupbuyAmountElt = $('input[name="buyAmount"]',$form);
	    		var buyAmount = groupbuyAmountElt.val();
	    		if(!buyAmount || buyAmount <= 0){
	    			groupbuyAmountElt.attr("value","1");
	    			groupbuyAmountElt.focus();
	    			return false;
	    		}
	    		if(!confirm("您确认参与合买？")){
	    			return false;
	    		} else {
	    			$.blockUI({  
	    			     overlayCSS:{backgroundColor:'#FFFFFF'},  
	    			     message:'方案已提交，正在处理中，请勿重复提交！',  
	    			     css:{  
	    				      backgroundColor:'#FFFFFF',  
	    				      height:50  
	    			     }  
	    		     }); 
	    			$.ajax({
	    				url: "http://trade.davcai.com/ac/betconfirm_in_weibo.do?jsonp=?", 
	    				data: $form.serialize(),
	    				success: function (json){
	    					var result = json.data;
	    					if(result && result.success == true) {
	    						var canBuy = $("span[_n='canBuy']",$context);
	    						var amount = canBuy.text() - buyAmount;
	    						canBuy.text(amount);
	    						$('input[name="buyAmount"]',$form).val(amount);
	    					}
	    					$.unblockUI({
	    						onUnblock:function (){
	    							if(result && result.success == true) {
	    								$.msgBox(result.data, null, {pos:'center', success: result.success});
	    								if(json.appCode == -1 || json.appCode == 0 || json.saleStatus != 0) {
	    									self.updateGroupTagProgress(json);
	    								}
	    								if(json.appCode == -1 || json.saleStatus != 0) {
	    									self.updateGroupBuyBtnInWeibo();
	    								}
	    								self.updateFollowersList($context,$form,buyAmount);
	    							} else {
	    								$.msgBox('操作失败！', null, {pos:'center', success: false});
	    							}
	    							$context.hide();
	    						}
	    					});
	    				},
	    				dataType:"jsonp"
	    			});
	    		}
	    	});
	    },
	    updateGroupTagProgress : function(json){
	    	var progressTag = $("span[_tagToSchemeId=" + json.schemeId + "]");
			if(progressTag && progressTag.length > 0) {
				var tagLabel = "进度";
				for(var index=0; index<progressTag.length; index++) {
					var tag = progressTag[index];
					var content = $(tag).text();
					if(content && content.indexOf(tagLabel) >= 0) {
						$(tag).text(tagLabel + json.progress + "%");
					}
				}
			}
	    },
	    updateGroupBuyBtnInWeibo : function(){
	    	this.$weiboGroupBuyBtn = this.options.appendPosi.closest("li[weiboMsgLi]").find('.groupbuy-button');
	    	this.$weiboGroupBuyBtn.hide();
	    },
	    updateFollowersList : function ($context,$form,buyAmount){
	    	var self = this;
	    	var $realFollowUserDiv = $context.closest("li[weiboMsgLi]").find('[_id="real-follower-div"]');
			$.getJSON('http://login.davcai.com/ajax_get_weibouser.do?jsonp=?', function(data){
	    		if (data != null) {
				   var weiboUser = data.weiboUser;
				   self.options.post.realFollowers.push({
						nickName:weiboUser.nickName,
						headImageURL:weiboUser.headImageURL,
						weiboUserId:weiboUser.weiboUserId,
						buyAmount:buyAmount
					});
					$realFollowUserDiv.realWeiboFollowUsers({
						postId:self.options.post.id,
						realFollowers:self.options.post.realFollowers
					});
	    		}
			});
	    }
	};
	
	$.fn.groupBuyRealWeiboDialog = function (option) {
		return this.each(function(input_field) {
			var $this = $(this), data = $this.data('groupBuyRealWeiboDialog');
		    if (!data) {
		    	$this.data('groupBuyRealWeiboDialog', (data = new groupBuyRealWeiboDialog(this, option)));
		    } else {
		    	data.reset(option);
		    }
		    //option 是方法名
		    if (typeof option == 'string'){
		    	if(!data[option]){
		    		console.log( 'Method ' + option + ' does not exist on jQuery.groupBuyRealWeiboDialog' );
		    	} else {
		    		data[option]();
		    	}
		    }
		});
	};
	$.fn.groupBuyRealWeiboDialog.defaults = {		
		post:null,
		appendPosi:null,
		currentUserId:null
	};
})(window.jQuery);

;(function() {
	$.extend({
		//按比赛类型区分不同订阅渠道
		matchType:function(options) {
			var lotteryId = options.lotteryId;
			var name = '';
			if(lotteryId == 'JCZQ') {
				name = 'FOOTBALL';
			} else if(lotteryId = 'JCLQ') {
				name = 'BASKETBALL';
			}
			return name;
		}
	});
})(window.jQuery)
;(function($) {
	var statusTool = function(element, options) {
	}
	statusTool.prototype = {
		status:function(status) {
			switch(status) {
            case 0:
            case 1:
            case 2:
                return "未出票";
            case 5100:
                return "出票成功";
            case 5101:
                return "出票失败";
            case 5202:
                return "未中奖";
            case 5203:
                return "中奖未派奖";
            case 12:
                return "已派奖";
            case 99:
                return "流标";
            case 100:
                return "撤单";
            default:
                return "未知状态";
			}
		},
		buyType:function(type) {
			switch (type) {
			case 0:
				type = "代购";
				break;
			case 1:
				type = "合买";
				break;
			case 2:
				type = "跟单";
				break;
			default:
                type = "未知类型";
			}
			return type;
		},
		detailScheme:function(type, showScheme, lotteryId, schemeId) {
			if(type == 1) {
				return 'http://trade.davcai.com/ac/groupbuy.do?id=' + schemeId;
			} else if(showScheme == 1) {
				return 'http://trade.davcai.com/ac/follow.do?id=' + schemeId;
			} else if(type == 0 && showScheme == 0 && (
				lotteryId == 'JCZQ' || lotteryId == 'JCLQ' ||
				lotteryId == 'CTZC' || lotteryId == 'SSQ' ||
				lotteryId == 'BJDC')) {
				return 'http://trade.davcai.com/ac/betdetail.do?id=' + schemeId;
			} else if(type == 2 && showScheme == 2) {
				return 'http://trade.davcai.com/ac/groupbuy.do?id=' + schemeId;
			} else if(type == 2 && showScheme == 0) {
				return 'http://trade.davcai.com/ac/betdetail.do?id=' + schemeId;
			}
		}
	};
	
	$.extend({
		statusTool:function (option) {
			return new statusTool(this, option);
		}
	});
})(window.jQuery);
/**
 * 彩种标签JS服务
 */
;(function($) {
	/**
	 * @param options 包含字段
	 *  	lotteryId
	 */
	var lotteryTab = function (element, options) {
		this.$elt = element;
		this.options = options;
		this.init();
	};
	lotteryTab.prototype = {
		init:function() {
			this.settings = {};
			$.extend(this.settings, LT);
			this.rend();
		},
		rend:function() {
			var lotteryId = this.options["lotteryId"];
			var dataList = this.options["data-list"];
			$("#"+dataList).betRecordPanel({"lotteryId":lotteryId});
			this.bind();
		},
		bind:function() {
			var self = this;
			$(self.$elt).click(function() {
				self.layout();
			});
		},
		layout:function() {
			var self = this;
			$(self.$elt).parent().children().removeClass("active");
			$(self.$elt).addClass("active");
			var self_data_Id = "#"+self.options["data-list"];
			$(self_data_Id).parent().children("div").hide();
			$(self_data_Id).show();
		}
	}
	$.fn.lotteryTab = function(option) {
		return this.each(function(input_field) {
			new lotteryTab(this, option);
		});
	};
})(window.jQuery);
;(function($) {
	/**
	 * 投注记录面板
	 */
	var betRecordPanel = function(element, options) {
	    this.$elt = element;
	    this.options = $.extend({}, $.fn.betRecordPanel.defaults, options);
	    this.initialized();
	};
	betRecordPanel.prototype = {
	    initialized : function(){
	    	this.settings = {};
	    	$.extend(this.settings, LT);
	    	this.rend();
	    },
	    rend : function(){
	    	if(this.options.lotteryId) return;
	    	var self = this;
	    	$(self.$elt).append('<div class="loading" style="height: 40px;"></div>');
	        $.ajax({
	        	type:"post",
	        	dataType:'json',
	        	data:self.options,
	    		url:this.options.betRecordUrl,
	    		success : function(json, e) {
	    			if(json) {
	    				self.view($(self.$elt), json.data);
	    				$(self.$elt).find(".loading").remove();
	    			}
	    		},
	    		error:function (data, e){
	    		}
	    	});
	    },
	    view:function(elt,data) {
	    	elt.betRecordView({
				"bet" : data.all,
				"lotteryId":self.options.lotteryId
			});
	    	$("#data-lottery-jz").betRecordView({
	    		"bet" : data.jczq,
	    		"lotteryId":"JCZQ"
	    	});
	    	$("#data-lottery-jl").betRecordView({
	    		"bet" : data.jclq,
	    		"lotteryId":"JCLQ"
	    	});
	    	$("#data-lottery-zc").betRecordView({
	    		"bet" : data.ctzc,
	    		"lotteryId":"CTZC"
	    	});
	    	$("#data-lottery-bd").betRecordView({
	    		"bet" : data.bjdc,
	    		"lotteryId":"BJDC"
	    	});
	    }
	};
	  	
	$.fn.betRecordPanel = function (option) {
		return this.each(function(input_field) {
			var $this = $(this), data = $this.data(option.toString());
		    if (!data) {
		    	$this.data(option.toString(), (data = new betRecordPanel(this, option)));
		    }
		});
	};
	$.fn.betRecordPanel.defaults = {
		betRecordUrl : LT.Settings.URLS.betRecord.ajaxUserBetRecord,
	};
})(window.jQuery);

$(function() {
	$("#betRecord").find("#tab-lottery-qb").lotteryTab({"lotteryId":"", "data-list":"data-lottery-qb"});
	$("#betRecord").find("#tab-lottery-jz").lotteryTab({"lotteryId":"JCZQ", "data-list":"data-lottery-jz"});
	$("#betRecord").find("#tab-lottery-jl").lotteryTab({"lotteryId":"JCLQ", "data-list":"data-lottery-jl"});
	$("#betRecord").find("#tab-lottery-zc").lotteryTab({"lotteryId":"CTZC", "data-list":"data-lottery-zc"});
	$("#betRecord").find("#tab-lottery-bd").lotteryTab({"lotteryId":"BJDC", "data-list":"data-lottery-bd"});
});
;(function($) {
	var betRecordView = function(element, options) {
		this.$elt = $(element);
		this.options = $.extend({}, $.fn.betRecordView.defaults, options);
		this.$tpl = $("#bet-record-tpl");
		this.$lottery = new lottery();
	    this.init();
	}
	betRecordView.prototype = {
		init:function() {
			this.rend();
		},
		rend:function() {
			if(typeof this.options.bet == 'string') {
				return;
			}
			var self = this;
			var dataFlag = '';
			if(this.options.bet) {
				var len = this.options.bet.length;
				if(len && len > 0) {
					dataFlag = true;
				}
			}
			var segment = $.mustache(this.$tpl.html(), {
				scheme:this.options.bet,
				tips:dataFlag,
				_lotteryId:self.options.lotteryId,
				betUrl:function() {
					var lotteryId = self.options.lotteryId;
					switch(lotteryId) {
					case '':
					case 'JCZQ':
		                return "http://trade.davcai.com/df/bet/jczq_80_zc_2.html";
					case 'JCLQ':
						return "http://trade.davcai.com/df/bet/jclq_06_lc_2.html";
					case 'CTZC':
						return "http://trade.davcai.com/df/bet/ctzc_24_zc_14.html";
					case 'BJDC':
						return "http://trade.davcai.com/df/bet/bjdc_spf.html";
					}
				},
				_showScheme:function() {
					var isPublishShow = this.isPublishShow;
					var showScheme = this.showScheme;
					if(showScheme == "1" || isPublishShow == "1") {
						return true;
					}
					return false;
				},
				_isShowAction:function() {
					if(this.lotteryId == "JCZQ" || this.lotteryId == "JCLQ") {
						return '';
					}
					return true;
				},
				_type:function() {
					return $.statusTool().buyType(this.type);
				},
				_status:function() {
					if(this.type == 1 && (this.status == 0 || 
							this.status == 1 || this.status == 2)) {
						return "进度" + this.progress + "%";
					} else {
						return $.statusTool().status(this.status);
					}
				},
				_lotteryName:function() {
					return self.$lottery.getName(this.lotteryId);
				},
				_createdTime:function() {
					return this.createdTime.substr(0, 10)
				},
				_winAmount:function() {
					if(this.status == 0 || 
						this.status == 1 || 
						this.status == 2 ||
						this.status == 5101) {
						return '--';
					}
					if(this.betRecordId && this.betRecordId > 0) {
						return this.groupWinAmount + "元";
					}
					return this.afterTaxBonus + "元";
				},
				_buyAmount:function() {
					if(this.betRecordId && this.betRecordId > 0) {
						return this.groupBetMount;
					}
					return this.buyAmount;
				},
				_detailSchemeUrl:function() {
					return $.statusTool().detailScheme(this.type, this.showScheme, 
							this.lotteryId, this.id);
				},
				switchHomeGuest:function() {
					if(this.lotteryId == "JCLQ") {
						return '客队VS主队';
					}
					return '主队VS客队';
				},
	    		switchHomeGuest:function() {
	    			if(this.lotteryId == "JCLQ") {
	    				return '客队VS主队';
	    			}
	    			return '主队VS客队';
	    		}
			});
			
			$(self.$elt).empty();
			$(self.$elt).append(segment);
			
			var schemeList = this.options.bet;
			var schemeViews = $(self.$elt).find('[data-type="scheme-view"]');
			for(var i=0; i<schemeList.length; i++) {
				var scheme = schemeList[i];
				$(schemeViews[i]).schemeView({
					scheme:scheme,
					tmpl:"#bet-record-scheme-tmpl"
				});
			}
			
			this.bind();
		},
		bind:function() {
			this.showScheme();
		},
		showScheme:function() {
			var self = this;
			//投注记录展开收起事件
			$(self.$elt).find(".lottery-sun-top").click(function(e) {
				var target = e.target || e.srcTarget; 
				var isA = $(target).is("a");
				if(isA) {
					var type = $(target).attr("type");
					if(type == "shareScheme") {
						var isShow = $(target).attr("isShow");
						if(!isShow || isShow == "false") {
							$.msgbox('方案需要晒单之后才能进行分享', $(target), {success:false});
							return;
						}
					}
					self.schemeAction($(target));
					return;
				}
				var jquery$this = $(this);
				var icon_right = "/assets/js/bet/images/red-right-triangle.png";
				var icon_down = "/assets/js/bet/images/red-bottom-triangle.png";
				var displayFlag = $(this).next().css("display");
				//保证快速点击时也能保证箭头和方案详情数据的一致性
				if(displayFlag == 'none') {
					$(this).next().slideDown("slow");
					$(this).find("img").attr({src:icon_down});
				} else {
					$(this).next().slideUp("slow");
					$(this).find("img").attr({src:icon_right});
				}
				
				var schemeType = $(this).attr("data-type");
				var schemeId = $(this).attr("data-scheme-id");
				var showScheme = $(this).attr("data-show-scheme");
				var recordElt = $(this);
				var betSchemeDetail = recordElt.parent().find('[data-type="bet-scheme-detail"]');
				var isData = betSchemeDetail.attr("_is_data");
				if(isData && isData == "true") {
					return;
				}
				
//				$(self).append('<div class="loading" style="height: 40px;"></div>');
//				$.ajax({
//		        	type:"post",
//		        	dataType:'json',
//		        	data:{"schemeId":schemeId, "schemeType":schemeType, "showScheme":showScheme},
//		    		url:self.options.ajaxViewBetScheme,
//		    		success : function(json, e) {
//		    			if(json && json.data) {
//		    				betSchemeDetail.schemeView({
//			        			scheme:json.data,
//			        			tmpl:"#bet-record-scheme-tmpl"
//			        		});
//		    				betSchemeDetail.attr("_is_data", "true");
//		    				jquery$this.next().slideDown("slow");
//		    				$(self).find(".loading").remove();
//		    			}
//		    		},
//		    		error:function (data, e){
//		    		}
//		    	});
			});
			//查看更多投注记录
			$(self.$elt).find("._seeMore").click(function() {
				var _lotteryId = $(this).attr("lotteryId");
				window.location.href = "http://trade.davcai.com/ac/record/bet.do";
			});
		},
		schemeAction:function(action) {
			var type = action.attr("type");
			var schemeId = action.attr("_schemeid");
			if(type == "detailScheme") {
				return;
			} else if(type == "showScheme") {
				var param = {"schemeId":schemeId};
				jQuery.ajax({
					type:"post",
					url:"http://www.davcai.com/bet/aj_showSchemeWeibo.do",
					data:param,
					dataType:"json",
					success: function(msg){
						var result = "失败";
						if(msg) {
							if(msg.success == true) {
								$.msgbox("晒单请求已发出", action, {success:true});
								var weiboUrl = msg.data.weiboUrl;
								action.parent().find("a[type='shareScheme']").attr("isshow", "true");
								action.parent().find("a[type='shareScheme']").attr("shareurl", weiboUrl);
								return;
							} else {
								result = msg.data.desc;
							}
						}
						$.msgbox(result, action, {success:false});
		            }
				});
				return;
			}
		}
	};
	
	$.fn.betRecordView = function (option) {
		return this.each(function(input_field) {
			new betRecordView(this, option);
		});
	};
	$.fn.betRecordView.defaults = {
		ajaxViewBetScheme : LT.Settings.URLS.betRecord.ajaxViewBetScheme,
	};
})(window.jQuery);
var marrowFormatData =  function(posts,userId) {
	var results = [];
	for(var i=0 ; i < posts.length ; i++ ){
		var obj = posts[i];
		var srcwb = false;
		if (obj.sourceWeibo == null || obj.sourceWeibo.id <= 0) {
			obj.sourceWeibo = obj.sourceWeiboMsg;
		}
		if(obj.sourceWeibo == null) {
			srcwb = null;
		} else {
			srcwb = {
					id : obj.sourceWeibo.id,
					ownerId : obj.sourceWeibo.ownerId,
					nickName : obj.sourceWeibo.user.nickName,
					title : obj.sourceWeibo.title || '',
					content : obj.sourceWeibo.content || '',
					createTime : $.dateFormat.date(new Date(obj.sourceWeibo.createTime), "yyyy-MM-dd HH:mm:ss"),
					commentCount : obj.sourceWeibo.commentCount,
					sourceBetScheme:obj.sourceBetScheme,
					sourceRealFollowers:obj.sourceRealFollowers,
					sourceType:obj.sourceType,
					certificationType:obj.sourceWeibo.user.certificationType
			};
		}
		if(obj && obj.user) {
			results.push({
				id : obj.id,
				ownerId : obj.ownerId,
				nickName : obj.user.nickName || '',
				headImageURL : obj.user.headImageURL || '',
				title : obj.title || '',
				type: obj.type,
				schameId : obj.schameId,
				content : obj.content || '',
				srcwb : srcwb,
				createTime : $.dateFormat.date(new Date(obj.createTime), "yyyy-MM-dd HH:mm:ss"),
				timeLine : obj.createTime,
				from :  obj.from ||'',
				fromUrl : obj.fromUrl || '',
				forwardCount : obj.forwardCount,
				shareCount : obj.shareCount,
				favoriateCount : obj.favoriateCount,
				commentCount : obj.commentCount,
				likeCount : obj.likeCount,
				isFavourited : obj.favourited,
				isLike : obj.like,
				isCurrentUser : userId !=0 && userId == obj.ownerId,
				tags:obj.tags,
				likeUsers:obj.likeUsers,
				realFollowers:obj.realFollowers,
				betScheme:obj.betScheme,
				certificationType:obj.user.certificationType
			});
		}
	}
	return results;
};
var ajaxRendWinList = function(page) {
	$('body,html').animate({scrollTop:0},0);
	var pager = {page:1};
	if(null != page && page > 0) {
		pager.page = page;
	}
	$(".status-list[all]").html('<div class="loading" style="height: 40px;"></div>');
	$.ajax(LT.Settings.URLS.marrow.ajaxWinList,{
		data:pager,
		success: function(data) {
			$(".status-list[all]").weiboList({
				posts:data.results,
				weiboUserId:data.userId,
				formatWeiboDataFunction:marrowFormatData
			});//渲染微博列表
			$(".status-list[all]").find('.loading').remove();
			$(".status-list[all]").append(ajaxPager("#", page, data.totalPages));
			$(".status-list[all]").find("ul.pager > li > a").click(function(e) {
				e.preventDefault();
				var page = $(this).attr("data-page");
				ajaxRendWinList(parseInt(page));
			});
		},
		dataType:'json'
	});
};
var ajaxRendFollowingWinList = function(page) {
	$('body,html').animate({scrollTop:0},0);
	var pager = {page:1};
	if(null != page && page > 0) {
		pager.page = page;
	}
	$(".status-list[myfollowing]").html('<div class="loading" style="height: 40px;"></div>');
	$.ajax(LT.Settings.URLS.marrow.ajaxFollowingWinList,{
		data:pager,
		success: function(data) {
			if(!data.userId){
				$("#pop-outer").fadeIn(800);
				$("#all").show();
				$(".status-list[myfollowing]").find(".loading").remove();
				return ;
			}
			$(".status-list[myfollowing]").weiboList({
				posts:data.results,
				weiboUserId:data.userId,
				formatWeiboDataFunction:marrowFormatData
			});//渲染微博列表
			$(".status-list[myfollowing]").find(".loading").remove();
			$(".status-list[myfollowing]").append(ajaxPager("#", page, data.totalPages));
			$(".status-list[myfollowing]").find("ul.pager > li > a").click(function(e) {
				e.preventDefault();
				var page = $(this).attr("data-page");
				ajaxRendFollowingWinList(parseInt(page));
			});
		},
		dataType:'json'
	});
};
$(document).ready(function (){
	$(".davcai-nav-classify").find("label").each(function (){
		$(this).click(function (event){
			$(".davcai-nav-classify").find("label").removeClass("current");
			var $this = $(event.target);
			$this.addClass("current");
			if($this.attr("_t") == "all"){
				$(".status-list[all]").show();
				$(".status-list[myfollowing]").hide();
				if($(".status-list[all]").html() == ""){
					ajaxRendWinList(1);
				}
			} else if($this.attr("_t") == "myfollowing"){
				$(".status-list[myfollowing]").show();
				$(".status-list[all]").hide();
				if($(".status-list[myfollowing]").html() == ""){
					ajaxRendFollowingWinList(1);
				}
			}
		});
	});
});
var ajaxLoadHotDiscuss = function(page) {
	$('body,html').animate({scrollTop:0},0);
	var pager = {page:1};
	if(null != page && page > 0) {
		pager.page = page;
	}
	$(".status-list[all]").html('<div class="loading" style="height: 40px;"></div>');
	$.ajax(LT.Settings.URLS.marrow.ajaxHotDiscussList,{
		data:pager,
		success: function(data) {
			$(".status-list[all]").weiboList({
				posts:data.results,
				weiboUserId:data.userId,
				formatWeiboDataFunction:marrowFormatData
			});//渲染微博列表
			$(".status-list[all]").find('.loading').remove();
			$(".status-list[all]").append(ajaxPager("#", page, data.totalPages));
			$(".status-list[all]").find("ul.pager > li > a").click(function(e) {
				e.preventDefault();
				var page = $(this).attr("data-page");
				ajaxLoadHotDiscuss(parseInt(page));
			});
		},
		dataType:'json'
	});
};
var ajaxRendLatestPublish = function(page) {
	$('body,html').animate({scrollTop:0},0);
	var pager = {page:1};
	if(null != page && page > 0) {
		pager.page = page;
	}
	$(".status-list[all]").html('<div class="loading" style="height: 40px;"></div>');
	$.ajax(LT.Settings.URLS.marrow.ajaxLatestPublishList,{
		data:pager,
		success: function(data) {
			$(".status-list[all]").weiboList({
				posts:data.results,
				weiboUserId:data.userId,
				formatWeiboDataFunction:marrowFormatData
			});//渲染微博列表
			$(".status-list[all]").find('.loading').remove();
			$(".status-list[all]").append(ajaxPager("#", page, data.totalPages));
			$(".status-list[all]").find("ul.pager > li > a").click(function(e) {
				e.preventDefault();
				var page = $(this).attr("data-page");
				ajaxRendLatestPublish(parseInt(page));
			});
		},
		dataType:'json'
	});
};
var ajaxRendRecommendWeibo = function(page,url,type,sort) {
	$('body,html').animate({scrollTop:0},0);
	var pager = {page:1};
	if(null != page && page > 0) {
		pager.page = page;
	}
	var $statusList = $(".status-list["+type+"]"); 
	$statusList.html('<div class="loading" style="height: 40px;"></div>');
	$.ajax(url + "?sortTime=" + sort.time,{
		data:pager,
		success: function(data) {
			$(".status-list["+type+"]").weiboList({
				posts:data.results,
				weiboUserId:data.userId,
				formatWeiboDataFunction:null
			});//渲染微博列表
			$statusList.find('.loading').remove();
			$statusList.append(ajaxPager("#", page, data.pageRequest.pageCount));
			$statusList.find("ul.pager > li > a").click(function(e) {
				e.preventDefault();
				var page = $(this).attr("data-page");
				ajaxRendRecommendWeibo(parseInt(page),url,type,sort);
			});
		},
		dataType:'json'
	});
};
var realWeiboPageReadyFunction  = function (ajaxUrl,pageUrl,type){
	var followCountSortVal = jQuery('.sortText[followCountSort]').attr("followCountSort");
	var timeSortVal = jQuery('.sortText[timeSort]').attr("timeSort");
	var buyAmountSortVal = jQuery('.sortText[buyAmountSort]').attr("buyAmountSort");
	var rateOfReturnSortVal = jQuery('.sortText[rateOfReturnSort]').attr("rateOfReturnSort");
	var sort = {
		followCountSort: followCountSortVal == "null" ? null : followCountSortVal,
		timeSort:timeSortVal == "null" ? null : timeSortVal,
		buyAmountSort:buyAmountSortVal == "null" ? null : buyAmountSortVal,
		rateOfReturnSort:rateOfReturnSortVal == "null" ? null : rateOfReturnSortVal,
		recentDays:jQuery("#date_range").val()
	};
	ajaxRendRealFollowWeibo(1,ajaxUrl,type,sort);
    jQuery('.sortText[sort_option]').click(function (){
    	var $this = $(this);
    	var sortType = $this.attr("sort_option");
    	var sortValue = $this.attr(sortType);
    	var sort = {
			followCountSort: sortType == "followCountSort" ? (sortValue == "desc" ? "asc":"desc" ) :null,
			timeSort:sortType == "timeSort" ? (sortValue == "desc" ? "asc":"desc" ) :null,
			buyAmountSort:sortType == "buyAmountSort" ? (sortValue == "desc" ? "asc":"desc" ) :null,
			rateOfReturnSort:sortType == "rateOfReturnSort" ? (sortValue == "desc" ? "asc":"desc" ) :null
		};
    	doRealWeiboSortFunction(sort,pageUrl);
    });
    jQuery("#date_range").change(function (){
    	var $this = $("[sort_option] > .current").parent();
    	var sortType = $this.attr("sort_option");
    	var sortValue = $this.attr(sortType);
    	var sort = {
			followCountSort: sortType == "followCountSort" ? sortValue:null,
			timeSort:sortType == "timeSort" ? sortValue:null,
			buyAmountSort:sortType == "buyAmountSort" ? sortValue:null,
			rateOfReturnSort:sortType == "rateOfReturnSort" ? sortValue:null
		};
    	doRealWeiboSortFunction(sort,pageUrl);
    });
};

var doRealWeiboSortFunction = function(sort,pageUrl){//执行排序筛选功能
	var recentDays = jQuery("#date_range").val();
	if(sort.followCountSort){
		window.location.href=pageUrl+"?followCountSort=" + sort.followCountSort + "&recentDays=" + recentDays;			
	} else if(sort.timeSort){
		window.location.href=pageUrl+"?timeSort=" + sort.timeSort+ "&recentDays=" + recentDays;
	} else if(sort.buyAmountSort){
		window.location.href=pageUrl+"?buyAmountSort=" + sort.buyAmountSort+ "&recentDays=" + recentDays;
	} else if(sort.rateOfReturnSort){
		window.location.href=pageUrl+"?rateOfReturnSort=" + sort.rateOfReturnSort+ "&recentDays=" + recentDays;
	}
}

var ajaxRendRealFollowWeibo = function(page,url,type,sort) {//ajax获取并渲染实单，带分页
	$('body,html').animate({scrollTop:0},0);
	var data = {page:1};
	if(null != page && page > 0) {
		data.page = page;
	}
	data = $.extend({},data,sort);
	var $statusList = $(".status-list["+type+"]"); 
	$statusList.html('<div class="loading" style="height: 40px;"></div>');
	$.ajax(url,{
		data:data,
		success: function(data) {
			$(".status-list["+type+"]").weiboList({
				posts:data.results,
				weiboUserId:data.userId,
				formatWeiboDataFunction:null
			});//渲染微博列表
			$statusList.find('.loading').remove();
			$statusList.append(ajaxPager("#", page, data.pageRequest.pageCount));
			$statusList.find("ul.pager > li > a").click(function(e) {
				e.preventDefault();
				var page = $(this).attr("data-page");
				ajaxRendRealFollowWeibo(parseInt(page),url,type,sort);
			});
		},
		dataType:'json'
	});
};

function backTop(){
	this.init();
}
backTop.prototype = {
	constructor: backTop,
	init: function(){		
		this._initBackTop();
	},	
	_initBackTop: function(){
		var $backTop = this.$backTop = $('<div class="cbbfixed">'+
											'<a class="gotop cbbtn">'+
												'<span class="up-icon"></span>'+
											'</a>'+
										'</div>');
		$('body').append($backTop);
		$backTop.click(function(){
			$("html, body").animate({
				scrollTop: 0
			}, 120);
		});
		var timmer = null;
		$(window).bind("scroll",function() {
            var d = $(document).scrollTop(),
            e = $(window).height();
            0 < d ? $backTop.css("bottom", "10px") : $backTop.css("bottom", "-90px");
			clearTimeout(timmer);
			timmer = setTimeout(function() {
                clearTimeout(timmer);
            },100);
	   });
	}
}
var backtop = new backTop();
/**
 * 微博通知的js
 * 定义通知的命名空间Notification
 * 
 * */
Jet().$package("Notification",function(J){
	var notifier = this;
	
	var observer = {
		atMe : null,
		commentMe : null,
		myFollowers : null,
		privateMsgs : null
	};
	//最后一次通知的时间
	var noticeLastTime = null;
	var reqInterval = 6000;//6秒
	
	/**
	 * 发请求检查是否有未读消息
	 */
	this.isHaveUnreadMessage = function() {
		var intervalTime = 0;
		if(noticeLastTime) {
			var now = new Date().getTime();
			intervalTime = now - noticeLastTime;
		}
//		console.log("----" + intervalTime);
		if(noticeLastTime && intervalTime <= reqInterval) {
			return;
		}
		noticeLastTime = new Date().getTime();
		var url = "http://www.davcai.com/notification";
		var param = {};
		var type = "jsonp";
		$.post(url, param, function(result) {
			if(result && result.success === true) {
				notifier.changeLabelStatus(result.data);
			} else {
				//check failure
			}
		}, type);
	};
	
	/**
	 * 根据后台返回的通知状态信息，重置“提到我的”，“评论我的”， “粉丝”等标签状态
	 */
	this.changeLabelStatus = function(notice) {
		if(notice) {
			var mentions = notice.mentions;
			var comments = notice.comments;
			var followers = notice.followers;
			var privateMsgs = notice.privateMsgs;
			var createdTime = notice.createdTime;
			if(!observer.atMe) {
				observer.atMe = $("#atMe");
			}
			if(!observer.commentMe) {
				observer.commentMe = $("#commentMe");
			}
			if(!observer.myFollowers) {
				observer.myFollowers = $("#myFollowers");
			}
			if(!observer.privateMsgs) {
				observer.privateMsgs = $("#privateMsg");
			}
			notifier.resetLabelStatus(observer.atMe, mentions);
			notifier.resetLabelStatus(observer.commentMe, comments);
			notifier.resetLabelStatus(observer.myFollowers, followers);
			notifier.resetPrivateMsgStatus(observer.privateMsgs, privateMsgs);
			//notifier.resetNote(observer.privateMsgs,privateMsgs);
		}
	};
	
	/**
	 * 重置首页左侧标签通知状态
	 */
	this.resetLabelStatus = function(noticeContainer, msgCount) {
		var sprites = noticeContainer.find(".sprites");
		if(msgCount > 0) {
			if(sprites.length == 0) {
				if(msgCount > 99) {
					msgCount = 99;
				}
				if(msgCount <= 0) {
					msgCount = '';
				}
				var signal = $('<i class="sprites notice"><span>' + msgCount + '</span></i>');
				noticeContainer.append(signal);
			}
		} else {
			sprites.remove();
		}
	};
	/**
	 * 重置头部私信状态
	 */
	this.resetPrivateMsgStatus = function(noticeContainer, msgCount) {
		if(msgCount > 0) {
			if(msgCount > 99) {
				msgCount = 99;
			}
			var signal = $('<i class="mail-tip"></i><em class="mail-num"><a href="http://www.davcai.com/private_msg" target="_blank">'+msgCount+'</a></em>');
			noticeContainer.html(signal);
			if(msgCount < 10){
				noticeContainer.find(".mail-num").css("margin-left","17px");
			}
		} else {
			noticeContainer.html('');
		}
	};
	
});

$(function() {
	window.onscroll = function() {
		Notification.isHaveUnreadMessage();
	}
	$(window).focus(function() {
		Notification.isHaveUnreadMessage();
	});
});