# CRUD
Запрос на сохранение объекта в базу:
    SpecialtyView view = new SpecialtyView();
    view.getRequest("-c, Manager");
    
Запрос на удаление объекта по id:
    SpecialtyView view = new SpecialtyView();
    view.getRequest("-d, 1");
    
Запрос на удаление объекта Specialty:
    SpecialtyView view = new SpecialtyView();
    view.getRequest("-d, Manager");
    
Запрос на чтение объекта по id:
    SpecialtyView view = new SpecialtyView();
    view.getRequest("-r, 1");
    
Запрос на чтение всех объектов:
    SpecialtyView view = new SpecialtyView();
    view.getRequest("-r");
    
Запрос на изменение объекта по id:
    SpecialtyView view = new SpecialtyView();
    view.getRequest("-u, 1, Java Developer");
