//生成菜单
var menuItem = Vue.extend({
    name: 'menu-item',
    props:{item:{}},
    template:[
        '<li>',
        '	<a v-if="item.menuType === 1" href="javascript:;">',
        '		<i v-if="item.icon != null" :class="item.icon"></i>',
        '		<span>{{item.name}}</span>',
        '		<i class="fa fa-angle-left pull-right"></i>',
        '	</a>',
        '	<ul v-if="item.menuType === 1" class="treeview-menu">',
        '		<menu-item :item="item" v-for="item in item.sublist"></menu-item>',
        '	</ul>',

        '	<a v-if="item.menuType === 2 && item.pcode === 0" :href="\'#\'+item.url">',
        '		<i v-if="item.icon != null" :class="item.icon"></i>',
        '		<span>{{item.name}}</span>',
        '	</a>',

        '	<a v-if="item.menuType === 2 && item.pcode != 0" :href="\'#\'+item.url"><i v-if="item.icon != null" :class="item.icon"></i><i v-else class="fa fa-circle-o"></i> {{item.name}}</a>',
        '</li>'
    ].join('')
});

//iframe自适应
$(window).on('resize', function() {
	var $content = $('.content');
	$content.height($('.main-sidebar').height() - 60);
	$content.find('iframe').each(function() {
		$(this).height($content.height());
	});
}).resize();
Vue.config.devtools = true;
//注册菜单组件
Vue.component('menuItem',menuItem);

var vm = new Vue({
	el:'#hyapp',
	data:{
		user:{},
		menuList:{},
		main:"/main",
		password:'',
		newPassword:'',
        nav:{url:"#",title:"主页"}
	},
	methods: {
		getMenuList: function (event) {
			$.getJSON("sys/menu/nav?_"+$.now(), function(r){
				vm.menuList = r.menuList;
			});
		},
		getUser: function(){
			$.getJSON("sys/user/info?_"+$.now(), function(r){
				vm.user = r.user;
			});
		},
		updatePassword: function(){
			layer.open({
				type: 1,
				skin: 'layui-layer-molv',
				title: "修改密码",
				area: ['550px', '270px'],
				shadeClose: false,
				content: jQuery("#passwordLayer"),
				btn: ['修改','取消'],
				btn1: function (index) {
					var data = "password="+vm.password+"&newPassword="+vm.newPassword;
					$.ajax({
						type: "POST",
					    url: "sys/user/password",
					    data: data,
					    dataType: "json",
					    success: function(result){
							if(result.code == 0){
								layer.close(index);
								layer.alert('修改成功', function(index){
									location.reload();
								});
							}else{
								layer.alert(result.msg);
							}
						}
					});
	            }
			});
		},
        donate: function () {
            layer.open({
                type: 2,
                title: false,
                area: ['806px', '467px'],
                closeBtn: 1,
                shadeClose: false,
                content: ['http://cdn.renren.io/donate.jpg', 'no']
            });
        },
		goBack:function () {
            //主动刷新路由
            var url = window.location.hash;
            //导航菜单展开
            $(".treeview-menu li").removeClass("active");
            $(".sidebar a[href='"+url+"']").parents("li").addClass("active");
            $("#nav_title li:gt(1)").remove();
            $('#mainIframe').attr('src',vm.main);
        }
	},
	created: function(){
		this.getMenuList();
		this.getUser();
	},
	updated: function(){
		//路由
		var router = new Router();
		routerList(router, vm.menuList);
		router.start();
	}
});



function routerList(router, menuList){
	for(var key in menuList){
		var menu = menuList[key];
		if(menu.menuType == 1){
			routerList(router, menu.sublist);
		}else if(menu.menuType == 2){
			router.add('#'+menu.url, function() {
				var url = window.location.hash;
				
				//替换iframe的url
			    vm.main = url.replace('#', '');
			    
			    //导航菜单展开
			    $(".treeview-menu li").removeClass("active");
			    $(".sidebar a[href='"+url+"']").parents("li").addClass("active");
                $("#nav_title li:gt(1)").remove();
			    vm.nav.title =$(".sidebar a[href='"+url+"']").text();
			    vm.nav.url = url;
			});
		}
	}
}

function refreshFrame() {
    $('#mainIframe').attr('src', vm.main);
	
}
