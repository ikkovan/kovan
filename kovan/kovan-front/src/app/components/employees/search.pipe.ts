import { Pipe, PipeTransform } from '@angular/core';
import { EmployeeModel } from 'src/app/Models/employee.model';

@Pipe({
  name: 'search'
})
export class SearchPipe implements PipeTransform {
/*
  transform(value: any, args?: any): any {
    if (!args) {
      return value;
    }
    return value.filter((val) => {
      let rVal = (val.id.toLowerCase().includes(args)) ;
      return rVal;
    })

  }
*/

  transform(value: EmployeeModel[], filterText?: string): EmployeeModel[] {
 
  filterText = filterText ? filterText.toLocaleLowerCase() : null;
  return filterText ? value.filter((e: EmployeeModel) =>
  (e.firstName+' '+e.lastName).toLocaleLowerCase().indexOf(filterText) !== -1 ||
  (e.id).toString().indexOf(filterText) !== -1 )
    :

  value;
  }
}
