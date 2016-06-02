class LayoutController{

	constructor(appModel, $stateParams,appConstant){
		'ngInject';
		this.model = appModel;
		this.$stateParams = $stateParams;
		this.styleClassContent = '';
		this.appConstant = appConstant;
	}

	closeOpenPanel(){
		if(this.model.classCloseOpenPanel === 'open'){
			this.model.renderSideBar = false;
			this.model.classCloseOpenPanel = 'closed';
			this.model.classContent = 'col-md-12  middleContent';
			this.styleClassContent = 'styleClass';
		}else{
			this.model.renderSideBar = true;
			this.model.classCloseOpenPanel = 'open';
			this.model.classContent = 'col-md-9 middleContent';
			this.model.classSideBar = 'col-md-3 middleContent';
			this.styleClassContent = '';
		}
	}
}
export default LayoutController;

