import { TestBed } from '@angular/core/testing';

import { TreeFunctionService } from './tree-function.service';

describe('TreeFunctionService', () => {
  let service: TreeFunctionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TreeFunctionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
