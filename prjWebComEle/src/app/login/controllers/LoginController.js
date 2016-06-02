class LoginController{
	constructor($state, appModel, appConstant){
		this.$state = $state;
		this.model = appModel;
		this.appConstant = appConstant;
		
		this.model.hideSideBar();
	}

	login(){
		this.$state.go('comele.mainMenu'
			// ,{
			// user: 'yo',
			// pass: 'yo',
			// loged: true}
		);
	}

}
export default LoginController;
