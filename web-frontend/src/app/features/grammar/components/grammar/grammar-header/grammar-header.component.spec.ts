import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GrammarHeaderComponent } from './grammar-header.component';

describe('GrammarHeaderComponent', () => {
  let component: GrammarHeaderComponent;
  let fixture: ComponentFixture<GrammarHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GrammarHeaderComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GrammarHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
