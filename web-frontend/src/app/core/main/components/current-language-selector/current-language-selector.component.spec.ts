import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CurrentLanguageSelectorComponent } from './current-language-selector.component';

describe('CurrentLanguageSelectorComponent', () => {
  let component: CurrentLanguageSelectorComponent;
  let fixture: ComponentFixture<CurrentLanguageSelectorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CurrentLanguageSelectorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CurrentLanguageSelectorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
