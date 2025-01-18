import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdvancedSearchPanelComponent } from './advanced-search-panel.component';

describe('AdvancedSearchPanelComponent', () => {
  let component: AdvancedSearchPanelComponent;
  let fixture: ComponentFixture<AdvancedSearchPanelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdvancedSearchPanelComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdvancedSearchPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
