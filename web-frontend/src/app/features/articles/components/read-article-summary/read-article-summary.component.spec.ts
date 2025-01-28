import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReadArticleSummaryComponent } from './read-article-summary.component';

describe('ReadArticleSummaryComponent', () => {
  let component: ReadArticleSummaryComponent;
  let fixture: ComponentFixture<ReadArticleSummaryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReadArticleSummaryComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReadArticleSummaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
