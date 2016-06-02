class AdminController{
	constructor(appModel){
		this.model = appModel;
		this.model.showSideBar();
	}
}

export default AdminController;
