

export class EmployeeModel {

    id       : number;
    firstName     : string;
    lastName : string;
    salaryTemplate: number; // kullanılacak şablon numarası
    isMarried : number; // ya burası bir enum olarak da tutulabilir ama hız kaybetmemek için böyle gidiyorum.
    isSpouseWorking : number;
    countChildren : number;
    taxBands : number; // vergi dilimi
}
