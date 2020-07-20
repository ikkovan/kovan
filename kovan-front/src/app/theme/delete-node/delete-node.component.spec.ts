import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteNodeComponent } from './delete-node.component';

describe('DeleteNodeComponent', () => {
  let component: DeleteNodeComponent;
  let fixture: ComponentFixture<DeleteNodeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DeleteNodeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeleteNodeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
