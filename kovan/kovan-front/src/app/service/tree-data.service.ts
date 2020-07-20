import { TreeData } from './tree-data.model';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TreeDataService {

  _dataChange = new BehaviorSubject<TreeData[]>(
    [{
      Id: 1,
      RuleName: 'VERGİLERİN HESAPLAMASI',
      RuleString: 'x,y,z,s,d,f vergileri hesaplanır.',
      Children: [
        {
          Id: 3,
          RuleName: 'MANTIKSAL İŞLEMLER',
          RuleString: 'Mantıksal işlemler yapılır.',
          Children: []
        },
        {
          Id: 4,
          RuleName: 'KESİNTİLER',
          RuleString: 'Kesinti 1',
          Children: [
            {
              Id: 5,
              RuleName: 'KESİNTİ ÖZEL DURUM',
              RuleString: 'Bu durum sağlanıyor mu?',
              Children: []
            }
          ]
        }
      ]
    },
    {
      Id: 2,
      RuleName: 'MAAŞ HESAPLA',
      RuleString: 'Maaş hesaplama son adımı',
      Children: [
        {
          Id: 6,
          RuleName: 'MATEMATİKSEL İŞLEMLER',
          RuleString: 'Son maaş için hesaplar',
          Children: []
        }
      ]
    }
  ]
  );


}
