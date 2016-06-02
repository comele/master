class MainMenuController{
	constructor(appModel, $state, $stateParams){
		'ngInject';
		this.model = appModel;
		this.$stateParams = $stateParams;
		this.$state = $state;

		this.model.hideSideBar();
		
		if(this.$stateParams === undefined){
			this.$state.go('comele.login');
		}
	}
}
export default MainMenuController;
